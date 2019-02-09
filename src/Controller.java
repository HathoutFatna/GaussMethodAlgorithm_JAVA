import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static java.lang.Math.abs;
import static javafx.scene.paint.Color.WHITE;

public class Controller {
    @FXML
    private   AnchorPane hh;
    double t=50;
    int v=0;
    @FXML
    public  void afficher(double A[][],double b[]){
        GridPane matrice = new GridPane();

           /* for (int k = 0; k < A.length; k++)
            {
                for (int j = 0; j < A.length; j++)
                    System.out.print(A[k][j] + " ");
                System.out.println("     " +b[k]);

            }*/
        GridPane.setRowSpan(matrice, A.length);
        GridPane.setColumnSpan(matrice, A.length*2+1);
        for (int i = 1; i <= A.length; i++) {
            int z=1;
            for (int j = 1; j <= A.length*2; j+=2) {

                TextField text = new TextField();
                text.setPrefWidth(35.0);
                text.setPrefHeight(25);
               text.setText((String.valueOf(A[i-1][z-1])));
                matrice.add(text, j, i);
                Label l = new Label();
                if (z==A.length) l.setText("X"+z+" =") ;
                else
                    l.setText(" X"+z+ " +");z++;
                matrice.add(l,j+1,i);


            }}
        for (int i=1;i<=A.length;i++){
            TextField text2 = new TextField();
            text2.setPrefWidth(50.0);
            text2.setPrefHeight(25);
           text2.setText(String.valueOf(b[i-1]));
            matrice.add(text2,(A.length*2)+1,i);

        }
        hh.getChildren().add(matrice);

        AnchorPane.setTopAnchor(matrice, 420.0);
        AnchorPane.setLeftAnchor(matrice, t);
        Label l=new Label();
        l.setText("Etape "+String.valueOf(v+1)+":");
        l.setMaxSize(300,300);
        hh.getChildren().add(l);
        AnchorPane.setTopAnchor(l,400.0);
        AnchorPane.setLeftAnchor(l,t);
        v++;
        t=t+400;
    }
    @FXML
    private TextField text;

   private static final double EPSILON = 1e-10;

    // Gaussian elimination with partial pivoting
    public double[] lsolve(double[][] A, double[] b) {
        int n = b.length;

        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (abs(A[i][p]) > abs(A[max][p])) {
                    max = i;
                }
            }

            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
            double   t    = b[p]; b[p] = b[max]; b[max] = t;

           // System.out.println("max ="+abs(A[p][max-1]));
            // singular or nearly singular
           /* if (Math.abs(A[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }*/

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }

        if (p==n-1) break;
        afficher(A,b);
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }



    @FXML
    private Button ok;
    @FXML
    private TextField nb_eq;

    @FXML
    GridPane  matrice = new GridPane();
    @FXML
    private  TextField nb_inc;

   Button calcul = new Button();
    @FXML
    public  void creer(ActionEvent event) {
        int a = Integer.valueOf(nb_eq.getText());
        int b = Integer.valueOf(nb_inc.getText());

        GridPane.setRowSpan(matrice, a);
        GridPane.setColumnSpan(matrice, b*2+1);
        for (int i = 1; i <= a; i++) {
                int z=1;
            for (int j = 1; j <= b*2; j+=2) {

                TextField text = new TextField();
                text.setPrefWidth(35.0);
                text.setPrefHeight(25);

                matrice.add(text, j, i);
                Label l = new Label();
                if (z==b) l.setText("X"+z+" =") ;
                else
                l.setText(" X"+z+ " +");z++;
                matrice.add(l,j+1,i);


            }}
               for (int i=1;i<=a;i++){
                TextField text2 = new TextField();
                   text2.setPrefWidth(50.0);
                   text2.setPrefHeight(25);
                matrice.add(text2,(b*2)+1,i);

                }


                hh.getChildren().add(calcul);
                AnchorPane.setTopAnchor(calcul,180.0);
                AnchorPane.setLeftAnchor(calcul,420.0);
            hh.getChildren().add(matrice);
            AnchorPane.setTopAnchor(matrice, 180.0);
            AnchorPane.setLeftAnchor(matrice, 50.0);

            calcul.setText("Résoudre Le Système");
            calcul.setPrefHeight(30.0);

            calcul.setPrefWidth(200.0);

            calcul.setOnAction(e -> calculer());
             calcul.setStyle("-fx-background-color: #1A626B");
             calcul.setTextFill(WHITE);



    }
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

        @FXML
        public void calculer (){
            int a = Integer.valueOf(nb_eq.getText());
            int b = Integer.valueOf(nb_inc.getText());
            double[][] A=new double[a][b];
            double[] B = new double[a];


            for (int i = 1; i <= a; i++) {

                for (int j = 1, z=1; j <=b*2+1 && z<=b; j+=2,z++) {

                    TextField t = new TextField();
                    t= (TextField) getNodeFromGridPane(matrice,j,i);
                    double u=Double.valueOf(t.getText());

                    A[i-1][z-1]= u;
                }}
            for (int i=1;i<=a;i++){
                TextField t2 = new TextField();
                t2= (TextField) getNodeFromGridPane(matrice,(b*2)+1,i);
                    B[i-1]=Double.valueOf(t2.getText());

            }
            //System.out.println(A);System.out.println(B);

            double[] x = lsolve(A, B);


            VBox h = new VBox(5);
            VBox h2 = new VBox(5);
           // print results

        /*    for (int j = 0; j < a; j++) {
                Label l1 = new Label();
                l1.setText("X"+j+1+" =");
                h2.getChildren().add(l1);
            }*/
            for (int i = 0; i < a; i++) {
                TextField text1 = new TextField();
                text1.setPrefWidth(60.0);
                text1.setPrefHeight(25);
                text1.setText(String.valueOf(x[i]));
                h.getChildren().add(text1);
                System.out.println(x[i]);
                Label text2 = new Label();
                text2.setPrefWidth(60.0);
                text2.setPrefHeight(25);
                text2.setText("X"+(i+1)+" =");
                h2.getChildren().add(text2);


            }



            //Table.add(h,0,0,1,1);
            hh.getChildren().addAll(h);
            AnchorPane.setTopAnchor(h, 175.0);
            AnchorPane.setLeftAnchor(h, 750.0);
            hh.getChildren().addAll(h2);
            AnchorPane.setTopAnchor(h2, 175.0);
            AnchorPane.setLeftAnchor(h2, 700.0);

        }


    }
