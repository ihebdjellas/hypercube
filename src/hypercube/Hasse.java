
package hypercube;
import java.util.ArrayList;
import java.util.Scanner;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import static org.graphstream.algorithm.Toolkit.*;
import org.graphstream.ui.view.Viewer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import javax.swing.JOptionPane;
import sun.security.util.Length;

public class Hasse {
public static Graph graph_hasse = new SingleGraph("hasse");
public static int p;
public static int niveau_cube(int[] code) {
        int niv = 0;

        for (int e : code) {
            if (e == 1) {
                niv++;
            }
        }
        return niv;
    }

    public static int niveau_grille(int[] code) {
        int niv = 0;

        for (int e : code) {

            niv = niv + e;

        }
        return niv;
    }
    //fonction qui calcule le coefficient binomial

    public static int bi_s_nomial(int n, int k, int s) {
        int res = 0;
        /*for(int i=0;i<=n;i++){
         res+= (int)(Math.pow(-1, i)*binomial(n,i)*binomial(n+k-(s+1)*i,n));  
         }*/
        if (k < 0 || n < 0) {
            return 0;
        } else {
            if (k == 0 ) {
                return 1;
            } else {
                return bi_s_nomial(n - 1, k, s) + bi_s_nomial(n - 1, k - 1, s) + bi_s_nomial(n - 1, k - 2, s);
            }
        }
        //return res;
    }

    public static int binomial(int n, int k) {
        if (n < 0) {
            return (int) (Math.pow(-1, k) * binomial(-n + k - 1, k));
        }


        if (k < 0 || (n < k && n >= 0)) {
            return 0;
        }
        int[][] h = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            h[i][0] = h[i][i] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                h[i][j] = (j == 0 ? 0 : h[i - 1][j - 1]) + (i == j ? 0 : h[i - 1][j]);
            }
        }
        return h[n][k];
    }

    //fonction donne la valeur binaire d'un entier
    public static int[] binaire(int a) {
        int[] t = new int[a];
        int z;
        int i = 0;
        while (a > 0) {

            t[i] = a % 2;
            //System.out.print( "  "+t[i]);
            a = a / 2;
            i++;
        }
        z = i;
        //System.out.println("apres");
        int[] resultat = new int[z];
        for (int j = 0; j < z; j++) {
            resultat[j] = t[i - 1];
            i--;

           
        }
        return resultat;
    }
    //fonction donne la valeur d'un entier en base 3

    public static int[] base_3(int a) {
        int[] t = new int[a];
        int z;
        int i = 0;
        while (a > 0) {

            t[i] = a % 3;
            //System.out.print( "  "+t[i]);
            a = a / 3;
            i++;
        }
        z = i;
        //System.out.println("apres");
        int[] resultat = new int[z];
        for (int j = 0; j < z; j++) {
            resultat[j] = t[i - 1];
            i--;

            //System.out.print( "  "+resultat[j]);
        }
        return resultat;
    }

    //fonction calcule le factoriel d'un nombre
    public static int fact(int k) {
        if (k == 0) {
            return 1;
        } else {
            return fact(k - 1) * k;
        }
    }
    //fonction pour generer la matrice des sommets
    public static int[][] gener_somm_cube(int p){
    int p2; 
    Double p3 = new Double(Math.pow(2, p));
        p2 = p3.intValue();
        int[] tab = new int[p2];
        for (int i = 0; i < p2; i++) {
            tab[i] = i;
        }
        int[][] mat = new int[p2][p2];
        int max_leng = 0;
        for (int i = 0; i < p2; i++) {
            mat[i] = binaire(tab[i]);
            if (mat[i].length > max_leng) {
                max_leng = mat[i].length;
            }

        }
        //pour rendre les binaires de meme taille
        int[][] sommets = new int[p2][max_leng];
        for (int i = 0; i < p2; i++) {

            if (mat[i].length < max_leng) {
                int s = 0;
                for (int k = max_leng - mat[i].length; k < max_leng; k++) {

                    sommets[i][k] = mat[i][s];
                    s++;
                }
                for (int j = 0; j < max_leng - mat[i].length; j++) {
                    sommets[i][j] = 0;
                }

            } else {
                for (int j = 0; j < max_leng; j++) {
                    sommets[i][j] = mat[i][j];
                }
            }
        }
        
        main_menu.jTextArea1.setText("");
        main_menu.jTextArea1.append( "la longeur des chaines binaires :" + max_leng+ "\n");
        main_menu.jTextArea1.append("l'ensemble de sommets est : \n");
        for (int i = 0; i < sommets.length; i++) {
            main_menu.jTextArea1.append(" \n");
            for (int j = 0; j < sommets[i].length; j++) {
                main_menu.jTextArea1.append("" + sommets[i][j]);
                //System.out.print(" " + sommets[i][j]);

            }
            
        }
         main_menu.jTextArea1.append(" \n");
      return sommets;  
    }
    
    //fonction pour generer la matrice des sommets
    public static int[][] gener_somm_grille(int p){
      int p2;
        Double p3 = new Double(Math.pow(3, p));
        p2 = p3.intValue();
        int[] tab = new int[p2];
        for (int i = 0; i < p2; i++) {
            tab[i] = i;
        }
        int[][] mat = new int[p2][p2];
        int max_leng = 0;
        for (int i = 0; i < p2; i++) {
            mat[i] = base_3(tab[i]);
            if (mat[i].length > max_leng) {
                max_leng = mat[i].length;
            }

        }
//pour rendre les sommets de meme taille
        int[][] sommets = new int[p2][max_leng];
        for (int i = 0; i < p2; i++) {

            if (mat[i].length < max_leng) {
                int s = 0;
                for (int k = max_leng - mat[i].length; k < max_leng; k++) {

                    sommets[i][k] = mat[i][s];
                    s++;
                }
                for (int j = 0; j < max_leng - mat[i].length; j++) {
                    sommets[i][j] = 0;
                }

            } else {
                for (int j = 0; j < max_leng; j++) {
                    sommets[i][j] = mat[i][j];
                }
            }
        }

        main_menu.jTextArea1.setText("");
        main_menu.jTextArea1.append( "la longeur des chaines binaires :" + max_leng+ "\n");
        main_menu.jTextArea1.append("l'ensemble de sommets est : \n");
        for (int i = 0; i < sommets.length; i++) {
            main_menu.jTextArea1.append("\n");
            for (int j = 0; j < sommets[i].length; j++) {

                main_menu.jTextArea1.append(" " + sommets[i][j]);

            }
        }
        main_menu.jTextArea1.append(" \n");
        return sommets;
    }
    public static double[][] set_hypercube(int[][] a,Graph gg) {
        double[][] position=new double[a.length][2];
        try{
        PrintStream pr=new PrintStream(new File("C://Users/ihebmcee/Documents/NetBeansProjects/hypercube/tikzpicture.txt"));
        System.setOut(pr);
        int[][] b = new int[p + 1][a.length];
        int[][] c = new int[p + 1][a.length];
        int[] somm_1 = new int[p + 1];
        double[] pos_atte=new double[p+1];
        
        //initialisations
        for (int i = 0; i < b.length; i++) {
            somm_1[i] = -1;
            pos_atte[i]=0;
            for (int j = 0; j < b[i].length; j++) {
                b[i][j] = -1;
                c[i][j] = -1;
            }
        }

        int max;
        max = binomial(p, p / 2);
        int[] diff = new int[p + 1];
        int[] som_niv = new int[p + 1];
        for (int i = 0; i < diff.length; i++) {
            som_niv[i] = binomial(p, i);
            diff[i] = max - binomial(p, i);
        }
        
        for (int i = 0; i < a.length; i++) {

            int niv;
            niv = niveau_cube(a[i]);

            int k = 0;
            while (k < (a.length) && b[niv][k] != -1) {
                k++;
            }
            if (k >= b[0].length) {
                k = b[0].length - 1;

            } else {
                b[niv][k] = 1;

            }
        }
        int kk = 1, k = 0;

        for (int i = 0; i < a.length; i++) {
            Node n = gg.getNode(Integer.toString(i + 1));
            int niv = niveau_cube(a[i]);


            kk = 1;

            while (kk < (a.length) && b[niv][kk] != -1) {
                kk++;
            }
            k = 0;
            while (k < (a.length) && c[niv][k] != -1) {
                k++;
            }
            if (kk < max) {
                if (niv == 0 || niv == p) {
                    if (max % 2 == 0) {
                        n.setAttribute("xyz", max / 2-0.5   , niv * 2, 0);
                        position[i][0]=(max / 2-0.5);
                        position[i][1]=(niv * 2);
                        String s="";
                 for(int w=0;w<a[i].length;w++){
                     s=s+a[i][w];
                 }
                        pr.println("\\draw [fill=black] ("+(max / 2-0.5)+","+(niv * 2)+") circle (1.5pt);");
                        pr.println("\\draw ("+(max / 2-0.5)+","+(niv * 2)+") node[right,above]{"+s+"};");
                    }
                    if (max % 2 == 1) {
                        n.setAttribute("xyz", max / 2 , niv * 2, 0);
                        position[i][0]=(max / 2);
                        position[i][1]=(niv * 2);
                        String s="";
                 for(int w=0;w<a[i].length;w++){
                     s=s+a[i][w];
                 }
                        pr.println("\\draw [fill=black] ("+(max / 2)+","+(niv * 2)+") circle (1.5pt);");
                        pr.println("\\draw ("+(max / 2)+","+(niv * 2)+") node[right,above]{"+s+"};");
                    }
                } else {
                    if(k==0){
                      double f = ((double)(max-som_niv[niv]) / 2  );
                    n.setAttribute("xyz", f, niv * 2, 0);
                    position[i][0]=f;
                    position[i][1]=(niv * 2);
                    String s="";
                 for(int w=0;w<a[i].length;w++){
                     s=s+a[i][w];
                 }
                    pr.println("\\draw [fill=black] ("+f+","+(niv * 2)+") circle (1.5pt);");
                    pr.println("\\draw ("+f+","+(niv * 2)+") node[right,above]{"+s+"};");
                    pos_atte[niv]=f;
                    }
                    else{
                      //double f = (pos_atte[niv] + (double)((diff[niv]/kk)) );
                        double f = (pos_atte[niv] + 1 );
                    n.setAttribute("xyz", f, niv * 2, 0);
                    position[i][0]=f;
                    position[i][1]=(niv * 2);
                    String s="";
                 for(int w=0;w<a[i].length;w++){
                     s=s+a[i][w];
                 }
                    pr.println("\\draw [fill=black] ("+f+","+(niv * 2)+") circle (1.5pt);");
                    pr.println("\\draw ("+f+","+(niv * 2)+") node[right,above]{"+s+"};");
                    pos_atte[niv]=f;
                    }
                    }
            } else {
                
                 n.setAttribute("xyz", k, niv * 2, 0);
                 position[i][0]=k;
                 position[i][1]=(niv * 2);
                 String s="";
                 for(int w=0;w<a[i].length;w++){
                     s=s+a[i][w];
                 }
                 pr.println("\\draw [fill=black] ("+k+","+(niv * 2)+") circle (1.5pt);");
                 pr.println("\\draw ("+k+","+(niv * 2)+") node[right,above]{"+s+"};");
                  
                }
            somm_1[niv] = 1;
            if (k >= b[0].length) {
                k = b[0].length - 1;
                
            } else {
                c[niv][k] = 1;
            }
            
    }
        
        //creation des arretes entre les niveaux
        int i1,j;
        for (int i = 0; i < a.length - 1; i++) {
            i1 = i + 1;
            while (i1 < a.length) {
                j = 0;
                int dif = 0;
                while (j < a[i].length) {
                    if (a[i][j] != a[i1][j]) {
                        dif += 1;
                    }
                    j += 1;
                }
                if (dif == 1) {
                    pr.println("\\draw ("+position[i][0]+","+position[i][1]+") -- ("+position[i1][0]+","+position[i1][1]+");");
                }
                i1++;
            }
        }
        
        }catch(FileNotFoundException f){
        System.out.println(f);
    }
    return position;    
    }
    
    
    
    //fonction hasse de l'hypercube
    
    public boolean palindrome(int[] tab1, int[] tab2){
        int i = 0;
        int j = tab1.length - 1;       
        while(i < tab1.length && tab1[i] == tab2[j]){
            ++i;
            --j;
        }
        if (i == tab1.length){
            return true;
        }
        else {
            return false;
        }
    }

    public static double[][] set_hypergrille(int[][] a,Graph gg) {
        double[][] position=new double[a.length][2];
        try{
        PrintStream pr=new PrintStream(new File("C://Users/ihebmcee/Documents/NetBeansProjects/hypercube/tikzpicture.txt"));
        System.setOut(pr);
        int[][] b = new int[2 * p + 1][a.length];
        int[][] c = new int[2 * p + 1][a.length];
        double[] pos_atte=new double[2*p+1];
        //initialisations
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                b[i][j] = -1;
            }
        }
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                c[i][j] = -1;
            }
        }

        int max;
        max = bi_s_nomial(p , p , 2);
        //System.out.println("max " + max);
        int[] diff = new int[2 * p + 1];
        int[] som_niv = new int[2 * p + 1];
        for (int i = 0; i < diff.length; i++) {
            som_niv[i] = bi_s_nomial(p , i, 2);
            diff[i] = max - som_niv[i];
            //System.out.print("  som_niv  " + som_niv[i]);
            //System.out.print("  diff  " + diff[i]);
        }

        //System.out.println("max"+ max);

        for (int i = 0; i < a.length; i++) {

            int niv;
            niv = niveau_grille(a[i]);

            int k = 0;
            while (k < (a.length) && b[niv][k] != -1) {
                k++;
            }
            if (k >= b[0].length) {
                k = b[0].length - 1;

            } else {
                b[niv][k] = 1;

            }
        }



        //System.out.println("coord :"+i+" x :"+((k+1)*10)+" y :"+(cpt*100));
        //n.setAttribute("ui.xyz", 1000,1000,0);
        //cpt=0;
        int kk = 1, k = 0;

        for (int i = 0; i < a.length; i++) {
            Node n = gg.getNode(Integer.toString(i + 1));
            int niv = niveau_grille(a[i]);


            kk = 1;

            while (kk < (a.length) && b[niv][kk] != -1) {
                kk++;
            }



            k = 0;
            while (k < (a.length) && c[niv][k] != -1) {
                k++;
            }
          
            if (kk < max) {
                if (niv == 0 || niv == 2*p+1) {
                   
                        n.setAttribute("xyz", max / 2  , niv * 2, 0);
                        position[i][0]=(max / 2);
                        position[i][1]=(niv * 2);
                        String s="";
                        for(int w=0;w<a[i].length;w++){
                        s=s+a[i][w];
                        }
                        pr.println("\\draw [fill=black] ("+(max / 2)+","+(niv * 2)+") circle (1.5pt);");
                        pr.println("\\draw ("+(max / 2)+","+(niv * 2)+") node[right,above]{"+s+"};");
                    
                } else {
                    if(k==0){
                      double f = ((double)(max-som_niv[niv]) / 2  );
                        n.setAttribute("xyz", f, niv * 2, 0);
                        position[i][0]=f;
                        position[i][1]=(niv * 2);
                        String s="";
                        for(int w=0;w<a[i].length;w++){
                        s=s+a[i][w];
                        }
                        pr.println("\\draw [fill=black] ("+f+","+(niv * 2)+") circle (1.5pt);");
                        pr.println("\\draw ("+f+","+(niv * 2)+") node[right,above]{"+s+"};");
                    pos_atte[niv]=f;
                    }
                    else{
                      //double f = (pos_atte[niv] + (double)((diff[niv]/kk)) );
                        double f = (pos_atte[niv] + 1 );
                        n.setAttribute("xyz", f, niv * 2, 0);
                        position[i][0]=f;
                        position[i][1]=(niv * 2);
                        String s="";
                        for(int w=0;w<a[i].length;w++){
                        s=s+a[i][w];
                        }
                        pr.println("\\draw [fill=black] ("+f+","+(niv * 2)+") circle (1.5pt);");
                        pr.println("\\draw ("+f+","+(niv * 2)+") node[right,above]{"+s+"};");
                    pos_atte[niv]=f;
                    }
                    

                }

                //System.out.println("  k1  " + i + " " + k);
            } else {
                
                 n.setAttribute("xyz", k, niv * 2, 0);
                        position[i][0]=k;
                        position[i][1]=(niv * 2);
                        String s="";
                        for(int w=0;w<a[i].length;w++){
                        s=s+a[i][w];
                        }
                        pr.println("\\draw [fill=black] ("+k+","+(niv * 2)+") circle (1.5pt);");
                        pr.println("\\draw ("+k+","+(niv * 2)+") node[right,above]{"+s+"};");
                //System.out.println("  k2  " + i + " " + k);   
                
                
            }
            
            if (k >= b[0].length) {
                k = b[0].length - 1;
                //System.out.println(" k "+k);
            } else {
                c[niv][k] = 1;
            }
            
            //System.out.println(" "+k+" "+i);
        }
        
        //creation des arretes entre les niveaux
        int i1,j;
        
        for (int i = 0; i < a.length - 1; i++) {
            i1 = i + 1;
            while (i1 < a.length) {
                j = 0;
                int dif = 0;
                while (j < a[i].length) {
                    if ((a[i][j] - a[i1][j]) == 2 || (a[i1][j] - a[i][j]) == 2) {
                        dif = 10;
                        break;
                    } else {
                        if ((a[i][j] - a[i1][j]) == 1 || (a[i1][j] - a[i][j]) == 1) {
                            dif += 1;
                        }
                    }

                    j += 1;
                }

                //if (j >= a[i].length) {
                if (dif == 1) {
                     pr.println("\\draw ("+position[i][0]+","+position[i][1]+") -- ("+position[i1][0]+","+position[i1][1]+");");
                    //graph.addEdge(, c1+(i+1), c2+(i1+1));
                    /*if (a[i][a[i].length - 1] != a[i1][a[i].length - 1]) {
                     g.addAttribute("ui.style", "fill-color: rgb(255, 0, 0);");
                     } else {
                     g.addAttribute("ui.style", "fill-color: rgb(0, 0, 255);");

                     }*/
                }
                i1++;
            }
        }
        
        }catch(FileNotFoundException f){
        System.out.println(f);
    }
    return position;
    }
    
    //fonction crée l'hypercube 
    public static void crea_hypercube(int[][] a,Graph gg) {
        int j = 0, i1;

        //graph.addAttribute("ui.stylesheet", "graph {fill-color: red;}");
        for (int i = 0; i < a.length; i++) {

            Node n = gg.addNode(Integer.toString(i + 1));

            String s = "";
            int cpt = 0;
            for (int e : a[i]) {
                s += e;
                if (e == 1) {
                    cpt++;
                }

                n.addAttribute("ui.label", s);
            }
        }
        

        //creation des arretes entre les niveaux
        
        for (int i = 0; i < a.length - 1; i++) {
            i1 = i + 1;
            while (i1 < a.length) {
                j = 0;
                int dif = 0;
                while (j < a[i].length) {
                    if (a[i][j] != a[i1][j]) {
                        dif += 1;
                    }
                    j += 1;
                }
                if (dif == 1) {
                    String c = "", c1 = "", c2 = "";
                    Edge g;
                    g = gg.addEdge(c + (i + 1) + (i1 + 1), c1 + (i + 1), c2 + (i1 + 1), false);
                    /*if (a[i][a[i].length - 1] != a[i1][a[i].length - 1]) {
                     g.addAttribute("ui.style", "fill-color: rgb(255, 0, 0);");
                     } else {
                     g.addAttribute("ui.style", "fill-color: rgb(0, 0, 255);");

                     }*/
                    g.addAttribute("ui.style", "fill-color: rgb(0, 0, 255);");
                }
                i1++;
            }
        }


        //graph.display(false);

    }

    //fonction pour créer l'hypergrille
    public static void crea_hypergrille(int[][] a,Graph gg) {
        int j = 0, i1;

        //graph.addAttribute("ui.stylesheet", "graph {fill-color: red;}");
        for (int i = 0; i < a.length; i++) {

            Node n = gg.addNode(Integer.toString(i + 1));

            String s = "";
            int cpt = 0;
            for (int e : a[i]) {
                s += e;
                if (e == 1) {
                    cpt++;
                }

                n.addAttribute("ui.label", s);



            }

            //n.addAttribute("ui.style", "fill-color: rgb(255, 0, 0);");
        }


        //creation des arretes entre les niveaux
        boolean b1 = true, b2 = true, b = true;
        for (int i = 0; i < a.length - 1; i++) {
            i1 = i + 1;
            while (i1 < a.length) {
                b1 = true;
                b2 = true;
                j = 0;
                b = true;

                int dif = 0;
                while (j < a[i].length) {
                    if ((a[i][j] - a[i1][j]) == 2 || (a[i1][j] - a[i][j]) == 2) {
                        dif = 10;
                        break;
                    } else {
                        if ((a[i][j] - a[i1][j]) == 1 || (a[i1][j] - a[i][j]) == 1) {
                            dif += 1;
                        }
                    }

                    j += 1;
                }

                //if (j >= a[i].length) {
                if (dif == 1) {
                    String c = "", c1 = "", c2 = "";
                    Edge g;
                    g = gg.addEdge(c + (i + 1) + (i1 + 1), c1 + (i + 1), c2 + (i1 + 1), false);
                    //graph.addEdge(, c1+(i+1), c2+(i1+1));
                    /*if (a[i][a[i].length - 1] != a[i1][a[i].length - 1]) {
                     g.addAttribute("ui.style", "fill-color: rgb(255, 0, 0);");
                     } else {
                     g.addAttribute("ui.style", "fill-color: rgb(0, 0, 255);");

                     }*/
                    g.addAttribute("ui.style", "fill-color: rgb(0, 0, 255);");
                }
                i1++;
            }
        }

        
        //graph.display(false);

    }
    //fonction pour la creation des sous graphes grille
    public static void crea_sous_graphe_g(int[][] sommets,Graph gg){
       int cpt = 0, nombre = 0;;
        int[] marque = new int[sommets.length];
        for (int i = 0; i < sommets.length; i++) {
            marque[i] = niveau_grille(sommets[i]);
        }

        Edge g;

        for (int i = 0; i < sommets.length; ++i) {
            int[] sommet_courant = sommets[i];
            ArrayList<int[]> voisinage = new ArrayList<int[]>();
            int voisin1[] = null;
            int voisin2[] = null;
            for (int jj = 1; jj < sommet_courant.length - 1; ++jj) {
                if (sommet_courant[jj] != 0) {
                    voisin1 = new int[sommet_courant.length];
                    voisin2 = new int[sommet_courant.length];

                    for (int k = 0; k < sommet_courant.length; ++k) {
                        voisin1[k] = sommet_courant[k];
                        voisin2[k] = sommet_courant[k];
                    }
                    voisin1[jj] = sommet_courant[jj - 1];
                    voisin1[jj - 1] = sommet_courant[jj];
                    voisin2[jj] = sommet_courant[jj + 1];
                    voisin2[jj + 1] = sommet_courant[jj];
                }
                voisinage.add(voisin1);
                voisinage.add(voisin2);
            }
            if (sommet_courant[0] != 0) {
                voisin1 = new int[sommet_courant.length];
                for (int k = 0; k < sommet_courant.length; ++k) {
                    voisin1[k] = sommet_courant[k];
                }
                voisin1[0] = sommet_courant[1];
                voisin1[1] = sommet_courant[0];
                voisinage.add(voisin1);
            }
            if (sommet_courant[sommet_courant.length - 1] == 1) {
                voisin1 = new int[sommet_courant.length];
                for (int k = 0; k < sommet_courant.length; ++k) {
                    voisin1[k] = sommet_courant[k];
                }
                voisin1[sommet_courant.length - 1] = sommet_courant[sommet_courant.length - 2];
                voisin1[sommet_courant.length - 2] = sommet_courant[sommet_courant.length - 1];
                voisinage.add(voisin1);
            }

            for (int[] voisin_possible : voisinage) {
                ArrayList<Integer> exist = new ArrayList<Integer>();
                if (voisin_possible != null) {
                    int pos = 0;
                    int taill = voisin_possible.length;
                    for (int ll = 0; ll < taill; ++ll) {
                        pos += Math.pow(3, taill - ll - 1) * voisin_possible[ll];
                    }
                    //pos = pos - 1;
                    if (marque[i] == marque[pos]) {

                        try {
                            String c = "", c1 = "", c2 = "";
                            g = gg.addEdge(c + (i + 1) + (pos + 1), c1 + (i + 1), c2 + (pos + 1), false);
                            g.addAttribute("ui.style", "fill-color: rgb(0, 255, 0);");
                            nombre++;

                        } catch (Exception e) {
                        }
                    }
                }
            }




        }
        main_menu.jTextArea1.append(" le nombre des arretes ajoutés : " + nombre+"\n");  
    }
    
    //fonction pour la creation des sous graphes
    public static void crea_sous_graphe(int[][] sommets,Graph gg){
       int cpt = 0, nombre = 0;;
            int[] marque = new int[sommets.length];
            for (int i = 0; i < sommets.length; i++) {
                for (int k = 0; k < sommets[i].length; k++) {
                    if (sommets[i][k] == 1) {
                        cpt++;
                    }
                }

                marque[i] = cpt;
                
                cpt = 0;
            }

            Edge g;

            for (int i = 0; i < sommets.length; ++i) {
                int[] sommet_courant = sommets[i];
                ArrayList<int[]> voisinage = new ArrayList<int[]>();
                int voisin1[] = null;
                int voisin2[] = null;
                for (int jj = 1; jj < sommet_courant.length - 1; ++jj) {
                    if (sommet_courant[jj] == 1) {
                        voisin1 = new int[sommet_courant.length];
                        voisin2 = new int[sommet_courant.length];

                        for (int k = 0; k < sommet_courant.length; ++k) {
                            voisin1[k] = sommet_courant[k];
                            voisin2[k] = sommet_courant[k];
                        }
                        voisin1[jj] = 0;
                        voisin1[jj - 1] = 1;
                        voisin2[jj] = 0;
                        voisin2[jj + 1] = 1;
                    }
                    voisinage.add(voisin1);
                    voisinage.add(voisin2);
                }
                if (sommet_courant[0] == 1) {
                    voisin1 = new int[sommet_courant.length];
                    for (int k = 0; k < sommet_courant.length; ++k) {
                        voisin1[k] = sommet_courant[k];
                    }
                    voisin1[0] = 0;
                    voisin1[1] = 1;
                    voisinage.add(voisin1);
                }
                if (sommet_courant[sommet_courant.length - 1] == 1) {
                    voisin1 = new int[sommet_courant.length];
                    for (int k = 0; k < sommet_courant.length; ++k) {
                        voisin1[k] = sommet_courant[k];
                    }
                    voisin1[sommet_courant.length - 1] = 0;
                    voisin1[sommet_courant.length - 2] = 1;
                    voisinage.add(voisin1);
                }

                for (int[] voisin_possible : voisinage) {
                    ArrayList<Integer> exist = new ArrayList<Integer>();
                    if (voisin_possible != null) {
                        int pos = 0;
                        int taill = voisin_possible.length;
                        for (int ll = 0; ll < taill; ++ll) {
                            pos += Math.pow(2, taill - ll - 1) * voisin_possible[ll];
                        }
                        //pos = pos - 1;
                        if (marque[i] == marque[pos]) {

                            try {
                                String c = "", c1 = "", c2 = "";
                                g = gg.addEdge(c + (i + 1) + (pos + 1), c1 + (i + 1), c2 + (pos + 1), false);
                                g.addAttribute("ui.style", "fill-color: rgb(0, 255, 0);");
                                nombre++;

                            } catch (Exception e) {
                            }
                        }
                    }
                }




            }
            
            main_menu.jTextArea1.append(" le nombre des arretes ajoutés : " + nombre+" \n"); 
    }
}
