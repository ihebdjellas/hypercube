/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
/**
 *
 * @author iheb_djellas
 */

public class main_menu extends javax.swing.JFrame {

    public static Graph graph = new SingleGraph("hypercube");
    public static Graph graph1 = new SingleGraph("hypercube");
    public static int p;
    public static DefaultTableModel model=new DefaultTableModel();
 
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
        
        jTextArea1.setText("");
        jTextArea1.append( "la longeur des chaines binaires :" + max_leng+ "\n");
        jTextArea1.append("l'ensemble de sommets est : \n");
        for (int i = 0; i < sommets.length; i++) {
            jTextArea1.append(" \n");
            for (int j = 0; j < sommets[i].length; j++) {
                jTextArea1.append("" + sommets[i][j]);
                //System.out.print(" " + sommets[i][j]);

            }
            
        }
         jTextArea1.append(" \n");
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

        jTextArea1.setText("");
        jTextArea1.append( "la longeur des chaines binaires :" + max_leng+ "\n");
        jTextArea1.append("l'ensemble de sommets est : \n");
        for (int i = 0; i < sommets.length; i++) {
            jTextArea1.append("\n");
            for (int j = 0; j < sommets[i].length; j++) {

                jTextArea1.append(" " + sommets[i][j]);

            }
        }
        jTextArea1.append(" \n");
        return sommets;
    }
    //fonction pour generer des triangles des hyperdeterminants
    public static void triangle_hyper(){
        double [][] triangle_p = new double [51][51]; //la matrice qui va contenir le triangle de pascal
        double [][] triangle_l = new double [51][51];
        double [][] triangle_s = new double [51][51];
        double [][] carré;
        int s = Integer.valueOf(jTextField7.getText());
        int ordre= Integer.valueOf(jTextField6.getText());
                for(int i = 0; i < 51; i++){ //dans le triangle de pascal,la premiأ¨re colonne (correspondant أ  k = 0) ne contient que des 1 
			triangle_p[i][0] = 1;
			triangle_p[i][i] = 1; // les أ©lأ©ments diagonaux du triangle valent 1 aussi 
                        triangle_l[i][0] = 1;
			triangle_l[i][i] = 1;
		}
                for (int i = 2; i < 51; i++){
			if (i % 2 == 0){ //quand on est dans une ligne paire (n pair)
				for (int j = 1; j < i/2; j++){
					triangle_p[i][j] = triangle_p[i-1][j-1] + triangle_p[i-1][j]; //on gأ©nأ¨re les أ©lأ©ments par la formule de rأ©currence 
					triangle_p[i][i-j] = triangle_p[i][j]; //on complأ¨te par la formule de la symأ©trie 
                                        triangle_l[i][j] = triangle_l[i-1][j-1] + triangle_l[i-1][j]; //on gأ©nأ¨re les أ©lأ©ments par la formule de rأ©currence 
					triangle_l[i][i-j] = triangle_l[i][j];
                                }
				triangle_p[i][i/2] = triangle_p[i-1][i/2-1] + triangle_p[i-1][i/2]; //l'أ©lement du milieu de la ligne 
                                triangle_l[i][i/2] = triangle_l[i-1][i/2-1] + triangle_l[i-1][i/2];
                        }
			else{
				for (int j = 1; j <= (i-1)/2; j++){ //pareil pour n impair
					triangle_p[i][j] = triangle_p[i-1][j-1] + triangle_p[i-1][j];
					triangle_p[i][i-j] = triangle_p[i][j];
                                        triangle_l[i][j] = triangle_l[i-1][j-1] + triangle_l[i-1][j];
					triangle_l[i][i-j] = triangle_l[i][j];
				}
			}
		}
                /*for(int i=0;i<triangle_p.length;i++){
                    System.out.println("");
                    for(int j=0;j<triangle_p[i].length;j++){
                    System.out.print("\t"+triangle_p[i][j]);
                    }
                }*/
                triangle_l[0][0]=2; //par convension
                for(int i=1;i<triangle_l.length;i++){
                    //System.out.println("");
                    for(int j=0;j<triangle_l[i].length;j++){
                        triangle_l[i][j]=(int)Math.pow(-1, i+j)*( triangle_l[i][j]+triangle_p[i-1][j]);
                        //System.out.print("\t"+triangle_l[i][j]);
                    }
                }
                
                int i=0,k=0,i1,j1,ligne_ecr=0;
                while(i<triangle_s.length){
                    i1=ligne_ecr;
                    j1=0;
                    k=i;
                    while(i1<triangle_s.length-1){
                        triangle_s[i1][j1]=triangle_l[k][j1];
                        triangle_s[i1+1][j1]=triangle_l[k][j1];
                        j1++;
                        i1++;
                        k++;
                    }
                    i++;
                    ligne_ecr+=2;
                }
                for(int j=0;j<triangle_s.length;j++){
                    for(int l=0;l<triangle_s[k].length;l++){
                        triangle_s[j][l]=triangle_s[j][l]*Math.pow(2, l-1);
                    }
                    //triangle_s[j][0]=triangle_s[j][0]*(1/2);
                }
                for(int b=0;b<triangle_s.length;b++){
                    System.out.println("");
                    for(int j=0;j<triangle_s[b].length;j++){
                        
                        System.out.print("\t"+triangle_s[b][j]);
                    }
                }
                System.out.println("// le carré ");
          carré=new double[triangle_s.length-2][s+1];
          for(int l=0;l<s-2;l++){
              int j=s;
              for(int m=0;m<s+1;m++){//lire les elements du triangle dans le sens inverse
                  carré[l][m]=triangle_s[l+2][j];
                  j--;
              }
              j=s;
              
                  for(int m=s-l-2;m<=((s+1)/2);m++){//re inverser les elements non nuls
                  
                  double z=carré[l][m];
                  carré[l][m]=carré[l][j];
                  carré[l][j]=z;
                  j--;
              }
          }
          for(int l=s-2;l<carré.length-1;l++){
              int j=0;
              while(triangle_s[l+2][j] != 0){//recuperer le dernier element du triangle
              j++;
          }
              //System.out.println(""+j);
          j--;
              //System.out.println(""+j);
          for(int n=carré[l].length-1;n>=0;n--){//justifier les s derniers éléments
              carré[l][n]=triangle_s[l+2][j];
              j--;
          }
          int o=l-1,p=s;
          while(j>=0){//faire monter les éléments restants
              
              carré[o][p]=carré[o][p]+triangle_s[l+2][j];
              if(p==0){
                  o--;
                  p=s;
                  j--;
              }
              else{
              j--;
              p--;
              }
          }
          }
           int cpt=1;
          for(int w=0;w<carré.length;w++){
                    System.out.println("");
                    for(int j=0;j<carré[w].length;j++){
                        cpt++;
                        System.out.print("\t"+carré[w][j]);
                        if(cpt==ordre){
                            jTextField8.setText(""+carré[w][j]);
                        }
                    }
                }
         
          
          
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
        jTextArea1.append(" le nombre des arretes ajoutés : " + nombre+"\n");  
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
            
            jTextArea1.append(" le nombre des arretes ajoutés : " + nombre+" \n"); 
    }
    
    
    //fonction pour afficher hasse

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

    //fonction pour afficher le triangle 
    public static void affich_triangle(int[][] triangle){
     jTextArea2.setText("");   
    jTextArea2.append(" le triangle associé est : \n");
        for (int i = 0; i < triangle.length; i++){
            jTextArea2.append("\n");
            for (int j = 0; j < triangle[i].length; j++){
               jTextArea2.append("\b"+triangle[i][j]+"\b"); 
            }
        }
        jTextArea2.append("\n");    
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
    //fonction pour exporter les triangles hypercubes dans un fichier text
    public static void export_txt_c(int[][]a){
        try{
           String d=System.getProperty("user.dir");
           System.out.print(d);
        PrintStream p=new PrintStream(new File("hypercube_triangle.txt"));
    
        System.setOut(p);
         p.print("{");
        //p.println("le triangle obtenu :");
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length-1;j++){
                p.print(a[i][j]+",");
            }
            p.print(a[i][a[i].length-1]);
            p.print(";");
        }
        p.print("}");
    }catch(FileNotFoundException f){
        System.out.println(f);
    }
    }
    
    //fonction pour exporter les triangles hypergrilles dans un fichier text
    public static void export_txt_g(int[][]a){
        try{
        PrintStream p=new PrintStream(new File("C://Users/ihebmcee/Documents/NetBeansProjects/hypercube/hypergrille_triangle.txt"));
        System.setOut(p);
        //p.println("le triangle obtenu :");
        p.print("{");
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length-1;j++){
                p.print(a[i][j]+",");
            }
            p.print(a[i][a[i].length-1]);
            p.print(";");
        }
        p.print("}");
        
        
    }catch(FileNotFoundException f){
        System.out.println(f);
    }
    }
    
    //fonction pour colorier les arretes des sous graphes 
    public static void color_arr_s_graph(Graph g){
        String[] couleur={"red","blue","green","black","yellow","grey","cyan","orange","brown"};
        int j=0;
        int[] deg_somm=new int[g.getNodeCount()];
        for(int i=0;i<deg_somm.length;i++){
            Node n=g.getNode(Integer.toString(i + 1));
            deg_somm[i]=n.getDegree();
            
        }
        boolean[] deja_col=new boolean[g.getEdgeCount()];
        for(int i=0;i<deja_col.length;i++){
            deja_col[i]=false;
        }
        int max_deg=0,ind_max=0;
        for(int i=0;i<deg_somm.length;i++){
            if(deg_somm[i]>max_deg){ max_deg=deg_somm[i];
            ind_max=i;
            }
        }
        boolean col=false;
        while((col==false)){
            int k=0;
            j=0;
            for (Edge e : g.getEachEdge()){
             int s,t;
            
            s = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            if(s==(ind_max-1)){
              e.addAttribute("ui.style", "fill-color: "+couleur[j]+ ";");
                j++; 
                deja_col[k]=true;
                deg_somm[s]--;
            }
            if(t==(ind_max-1)){
                e.addAttribute("ui.style", "fill-color: "+couleur[j]+ ";");
                j++; 
                deja_col[k]=true;
                deg_somm[t]--;
            }
            k++;
            }
            deg_somm[ind_max]=0;
            max_deg=0;ind_max=0;
            for(int i=0;i<deg_somm.length;i++){
            if(deg_somm[i]>max_deg){ max_deg=deg_somm[i];
            ind_max=i;
            }
        }
            int s;
         for(s=0;s<deja_col.length;s++){
             if(deja_col[s]==false) break;
         }
         if(s==deja_col.length-1) col=true;
        }
        
        /*boolean col=false;
        boolean[] deja_col=new boolean[g.getEdgeCount()];
        for(int i=0;i<deja_col.length;i++){
            deja_col[i]=false;
        }
        int i=0;
        while((col==false)){
         //for(int i=0;i<Math.pow(2, p);i++){
            
         j=0;
         int k=0;
         int[] arr_som=new int[g.getEdgeCount()];
         for(int o=0;o<arr_som.length;o++){
            arr_som[o]=0;
        }
         for (Edge e : g.getEachEdge()){
             int s,t;
            
            s = Integer.valueOf(e.getSourceNode().getId());
            if(s==(i-1)){
              arr_som[k]=1;  
            }
            k++;
         }
         for(int o=0;o<arr_som.length;o++){
             Edge e=g.getEdge(o);
             if((arr_som[o]==1)&&(deja_col[o]==false)){
               e.addAttribute("ui.style", "fill-color: "+couleur[j]+ ";");
                j++; 
                deja_col[o]=true;
             }
         }
         int s;
         for(s=0;s<deja_col.length;s++){
             if(deja_col[s]==false) break;
         }
         if(s==deja_col.length-1) col=true;
         
         i++;
         }*/
     /*for (Edge e : g.getEachEdge()) {
            int s,t;
            
            s = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            System.out.println("i "+i+" s "+s+" t "+t);
            if ((s==(i-1) || t==(i-1))&&(deja_col[k]==false)) {
                e.addAttribute("ui.style", "fill-color: "+couleur[j]+ ";");
                j++;
                System.out.println("j "+j);
                deja_col[k]=true;
                System.out.println("arr"+k+" "+deja_col[k]);
            }
            k++;
            
        }*/
     }

    //fonction pour calculer l'hyperdeterminant
    public static int hyperdeterminant(int[][] sommets,int[] appar){
        int det=0;
        int sum_ind;
        for(int i=0;i<sommets.length/2;i++){
            sum_ind=0;
            for(int j=1;j<sommets[i].length;j++){
                sum_ind=sum_ind+sommets[i][j]; //recuperer la somme des indices du sommet courant
            }
            det = det +(int) Math.pow(-1, sum_ind)*appar[i]*appar[sommets.length-i-1];
        }
        return det;
    }
    
    //fonction pour calculer l'hyperdeterminant grille
    public static int hyperdeterminant_g(int[][] sommets,int[] appar,int p){
        if(p==0||p==1||p==2){
            return 0;
        }
        else{
        int det=0;
        int sum_ind;
        for(int i=0;i<sommets.length/3;i++){
            //recuperation des sommets de l'hypercube de dim inferieur
            int[][] sommets1=new int[(int)(Math.pow(2, p))][sommets[0].length];
            int[] appar1=new int[sommets1.length];
            int l=0;
            for(int k=0;k<sommets.length;k++){
                int s=0;
                for(int j=0;j<sommets[0].length;j++){
                    if(sommets[i][j]!=sommets[k][j]){
                        s++;
                    }
                }
                if(s==sommets[i].length){
                    sommets1[l]=sommets[k];
                    appar1[l]=appar[k];
                    l++;
                }
            }
            /*System.out.println("sommet "+i);
            for(int x=0;x<sommets1.length;x++){
                System.out.println("");
                for(int y=0;y<sommets1[x].length;y++){
                    System.out.print(" "+sommets1[x][y]);
                }
            }*/
            sum_ind=0;
            for(int j=1;j<sommets[0].length;j++){
                sum_ind=sum_ind+sommets[i][j]; //recuperer la somme des indices du sommet courant
            }
            
            det = det +(int) Math.pow(-1, sum_ind)*hyperdeterminant(sommets1,appar1);
            //System.out.println("det "+det);
        }
        return det;
    }
    }
    
    public main_menu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton25 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("main menu");
        setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setText("Dimension :");

        jButton1.setText("Hypercube");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Dimension :");

        jButton2.setText("Cube Tribonacci");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Dimension :");

        jButton3.setText("Cube Fibonacci");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("Dimension :");

        jButton4.setText("Hypergrille");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Hasse");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("3D ");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton3);
        jRadioButton3.setText("Hasse");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup2.add(jRadioButton4);
        jRadioButton4.setText("3D");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        buttonGroup3.add(jRadioButton5);
        jRadioButton5.setText("Hasse");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        buttonGroup3.add(jRadioButton6);
        jRadioButton6.setText("3D");
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton7);
        jRadioButton7.setText("Hasse");
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton8);
        jRadioButton8.setText("3D");
        jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton8ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lucida Calligraphy", 2, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("Hypercubes");

        jLabel6.setFont(new java.awt.Font("Lucida Calligraphy", 2, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Hypergrilles");

        jCheckBox1.setText("Arretes sous graphes");
        jCheckBox1.setActionCommand("");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Arretes sous graphes");
        jCheckBox2.setActionCommand("");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("Arretes sous graphes");
        jCheckBox3.setActionCommand("");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setText("Arretes sous graphes");
        jCheckBox4.setActionCommand("");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        jButton5.setText("Hypergrille Contrainte_11");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Hypergrille contrainte_11_222");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Personnaliser votre contrainte ...");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Lucida Calligraphy", 0, 14)); // NOI18N
        jLabel7.setText("Hypergrille Avec Contraintes ");

        jButton8.setText("Personnaliser votre contrainte ...");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel8.setText("Résultats :");

        jButton9.setText("Sous Graphes");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Sous Graphes");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Sous Graphes");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Sous Graphes");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Sous Graphes");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Sous Graphes");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel9.setText("Triangle  :");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jButton15.setText("Triangle");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Triangle");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setText("Triangle");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("Triangle");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setText("Hyperdeterminant");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel10.setText("Hyperdeterminant :");

        jButton20.setText("Hyperdeterminant");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setText("Hyperdeterminant");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setText("Hyperdeterminant");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setText("Hyperdeterminant");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setText("Hyperdeterminant");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Lucida Calligraphy", 2, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("Hyperdeterminants");

        jLabel12.setText("Dimension :");

        jButton25.setText("Hyperdeterminant");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jLabel13.setText("Nombre de 1's à éliminer :");

        jLabel14.setText("Résultat :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jRadioButton2))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jLabel3)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGap(18, 18, 18)
                                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(18, 18, 18)
                                                            .addComponent(jButton9))
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGap(38, 38, 38)
                                                            .addComponent(jCheckBox1))
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGap(18, 18, 18)
                                                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(18, 18, 18)
                                                            .addComponent(jButton10))))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jLabel1)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(118, 118, 118)
                                                    .addComponent(jButton19)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(22, 22, 22)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jButton20)
                                                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGap(37, 37, 37)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                    .addComponent(jLabel10)
                                                                    .addGap(0, 0, Short.MAX_VALUE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addGap(6, 6, 6))))
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(jButton24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jButton23, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jRadioButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jCheckBox2))
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(202, 202, 202)
                                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel2)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jButton11)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                    .addComponent(jButton6)
                                                                    .addGap(29, 29, 29)
                                                                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(jRadioButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jRadioButton6)
                                                                .addGap(72, 72, 72)
                                                                .addComponent(jCheckBox3))
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel4)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(160, 160, 160))
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jRadioButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jRadioButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jCheckBox4)
                                                                .addGap(38, 38, 38))))
                                                    .addGap(31, 31, 31)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jButton12)))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(206, 206, 206)
                                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(89, 89, 89))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel12)
                                                                .addComponent(jLabel13))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                                                .addComponent(jTextField7))
                                                            .addGap(49, 49, 49)))
                                                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(73, 73, 73)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField8)
                                                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(48, 48, 48)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(41, 41, 41)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(35, 35, 35)
                                            .addComponent(jLabel9)))
                                    .addGap(52, 52, 52))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel5)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton9)
                    .addComponent(jButton19))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3)
                            .addComponent(jButton10)
                            .addComponent(jButton15)
                            .addComponent(jButton20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton4)
                            .addComponent(jRadioButton3)
                            .addComponent(jCheckBox2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2)
                                    .addComponent(jButton11)
                                    .addComponent(jButton16)
                                    .addComponent(jButton21))))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jRadioButton6)
                                .addComponent(jRadioButton5))
                            .addComponent(jCheckBox3))
                        .addGap(18, 18, 18)
                        .addComponent(jButton8)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton7)
                            .addComponent(jRadioButton8)
                            .addComponent(jCheckBox4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4)
                            .addComponent(jButton12)
                            .addComponent(jButton22))
                        .addGap(22, 22, 22)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton14)
                            .addComponent(jButton17)
                            .addComponent(jButton23)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton1))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel10)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton13)
                    .addComponent(jButton18)
                    .addComponent(jButton24))
                .addGap(32, 32, 32)
                .addComponent(jButton7)
                .addGap(23, 23, 23)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jButton25)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /*int[][] sommets;
        p = Integer.valueOf(jTextField1.getText());
        sommets=gener_somm_cube(p);
        crea_hypercube(sommets,graph);
        if (jCheckBox1.getActionCommand() == "ok") {
            //traitement des sous graphes
            crea_sous_graphe(sommets,graph);
        }
        
       //set_hypercube(sommets,graph);*/
        

        if (buttonGroup1.getSelection().getActionCommand() == "hasse") {
            Hasse h=new Hasse();
            int[][] sommets;
            
            h.p= Integer.valueOf(jTextField1.getText());
            sommets=h.gener_somm_cube(h.p);
            h.crea_hypercube(sommets, h.graph_hasse);
            if (jCheckBox1.getActionCommand() == "ok") {
            //traitement des sous graphes
            h.crea_sous_graphe(sommets,h.graph_hasse);
        }
            h.set_hypercube(sommets, h.graph_hasse);
            
            Viewer viewer = h.graph_hasse.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    h.graph_hasse = new SingleGraph("hasse");
        } else {
            Viewer viewer = graph.display();
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        p = Integer.valueOf(jTextField2.getText());
        int[][] sommets;
        sommets=gener_somm_cube(p);
        double[][] position=new double[sommets.length][2];
        crea_hypercube(sommets,graph);

        if (jCheckBox3.getActionCommand() == "ok") {
            //traitement des sous graphes
            crea_sous_graphe(sommets,graph);
        }

        set_hypercube(sommets,graph);
        position=set_hypercube(sommets,graph);
        //traitement ..
        int[][] triangle=new int[p+1][p];
         
        for(int s=0;s<=p;s++){
            int[][] sommets1;
        sommets1=gener_somm_cube(s);
        int[] somm_cont=new int[s+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        int[] appar = new int[sommets1.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets1.length; i++) {
            
            for (int j = 0; j < sommets1[i].length - 2; j++) {
                if (sommets1[i][j] == 1 && sommets1[i][j + 1] == 1 && sommets1[i][j + 2] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            int niv=0;
            
            if (appar[i] == 1) {
                
                cpt++;
                niv=niveau_cube(sommets1[i]);
                somm_cont[niv]++;
            }
            
        }
        triangle[s]=somm_cont;
        }
        //affichage du triangle
        affich_triangle(triangle);
        
        
        int[] somm_cont=new int[p+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            Node n = graph.getNode(Integer.toString(i + 1));
            for (int j = 0; j < sommets[i].length - 2; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1 && sommets[i][j + 2] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            int niv=0;
            Node n = graph.getNode(Integer.toString(i + 1));
            if (appar[i] == 1) {
                n.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                cpt++;
                niv=niveau_cube(sommets[i]);
                somm_cont[niv]++;
            }
            else{
                n.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre de sommets tribonacci par niveau est :  \n");
        for (int i = 0; i < somm_cont.length; i++) {
            jTextArea1.append(" niveau "+i+" : "+ somm_cont[i] +" \n");
        }
        jTextArea1.append("le nombre de sommets tribonacci total est :"+cpt+" \n");
        try{
        PrintStream pr=new PrintStream(new File("C://Users/ihebmcee/Documents/NetBeansProjects/hypercube/tikzpicturecoloration.txt"));
        System.setOut(pr);
        int cpt1 = 0;
        for (Edge e : graph.getEachEdge()) {
            int i, t;
            i = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            if (appar[i - 1] == 1 && appar[t - 1] == 1) {
                e.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                pr.append("\\draw[color=red] ("+position[i-1][0]+","+position[i-1][1]+") -- ("+position[t-1][0]+","+position[t-1][1]+");");
                pr.println("");
                cpt1++;
            } else {
                e.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
                pr.append("\\draw[color=gray] ("+position[i-1][0]+","+position[i-1][1]+") -- ("+position[t-1][0]+","+position[t-1][1]+");");
                pr.println("");
            }
        }
        
        jTextArea1.append("le nombre des arretes tribonacci " + cpt1+"\n");
        }catch(FileNotFoundException f){
        System.out.println(f);
        }
        if (buttonGroup3.getSelection().getActionCommand() == "hasse") {
              Viewer viewer = graph.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
   
        } else {
            Viewer viewer = graph.display();
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        p = Integer.valueOf(jTextField3.getText());
        int[][] sommets;
        sommets=gener_somm_cube(p);
        double[][] position=new double[sommets.length][2];
        crea_hypercube(sommets,graph);
        if (jCheckBox2.getActionCommand() == "ok") {
            //traitement des sous graphes
            crea_sous_graphe(sommets,graph);
        }

        set_hypercube(sommets,graph);
        position=set_hypercube(sommets,graph);
        
        //traitement du triangle ...
        int[][] triangle=new int[p+1][p];
         
        for(int s=0;s<=p;s++){
            int[][] sommets1;
        sommets1=gener_somm_cube(s);
          
         int[] appar = new int[sommets1.length];
        int[] somm_cont=new int[s+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets1.length; i++) {
            
            for (int j = 0; j < sommets1[i].length - 1; j++) {
                if (sommets1[i][j] == 1 && sommets1[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int c=0,niv;
        for (int i = 0; i < appar.length; i++) {
            niv=0;
            if (appar[i] == 1) {
                c++;
                niv=niveau_cube(sommets1[i]);
                somm_cont[niv]++;
            }    
        }
             triangle[s]=somm_cont;  
        }
        //affichage du triangle
        affich_triangle(triangle);
        
        
        //affichage du graph d'ordre demandé
        
        int[] appar = new int[sommets.length];
        int[] somm_cont=new int[p+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            Node n = graph.getNode(Integer.toString(i + 1));
            for (int j = 0; j < sommets[i].length - 1; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int c=0,niv;
        for (int i = 0; i < appar.length; i++) {
            niv=0;
            Node n = graph.getNode(Integer.toString(i + 1));
            
            if (appar[i] == 1) {
                n.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                c++;
                niv=niveau_cube(sommets[i]);
                somm_cont[niv]++;
            }
            else{
                n.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
            
        }
        jTextArea1.append("le nombre de sommets fibonacci par niveau est :  \n");
        for (int i = 0; i < somm_cont.length; i++) {
            jTextArea1.append(" niveau "+i+" : "+ somm_cont[i] +" \n");
        }
        jTextArea1.append("le nombre de sommets fibonacci total est :"+c+" \n");
        try{
        PrintStream pr=new PrintStream(new File("C://Users/ihebmcee/Documents/NetBeansProjects/hypercube/tikzpicturecoloration.txt"));
        System.setOut(pr);    
        for (Edge e : graph.getEachEdge()) {
            int i, t;
            i = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            if (appar[i - 1] == 1 && appar[t - 1] == 1) {
                e.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                pr.append("\\draw[color=red] ("+position[i-1][0]+","+position[i-1][1]+") -- ("+position[t-1][0]+","+position[t-1][1]+");");
                pr.println("");
            } 
            else {
                e.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
                pr.append("\\draw[color=gray] ("+position[i-1][0]+","+position[i-1][1]+") -- ("+position[t-1][0]+","+position[t-1][1]+");");
                pr.println("");
            }
        }
        }catch(FileNotFoundException f){
        System.out.println(f);
        }
        

        if (buttonGroup2.getSelection().getActionCommand() == "hasse") {
            Viewer viewer = graph.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        } else {
            Viewer viewer = graph.display();
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        p = Integer.valueOf(jTextField4.getText());
        int[][] sommets;
        sommets=gener_somm_grille(p);

        crea_hypergrille(sommets,graph);
        if (jCheckBox4.getActionCommand() == "ok"){
           //traitement des sous graphes
        crea_sous_graphe_g(sommets,graph); 
        }
        set_hypergrille(sommets,graph);
        
        if (buttonGroup4.getSelection().getActionCommand() == "hasse") {
            Viewer viewer = graph.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        } else {
            Viewer viewer = graph.display();
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        jRadioButton1.setActionCommand("hasse");
        jRadioButton1.setSelected(true);
        jRadioButton2.setSelected(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        jRadioButton2.setActionCommand("3D");
        jRadioButton2.setSelected(true);
        jRadioButton1.setSelected(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        jRadioButton3.setActionCommand("hasse");
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
        jRadioButton6.setActionCommand("3D");
    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        jRadioButton5.setActionCommand("hasse");
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton7ActionPerformed
        jRadioButton7.setActionCommand("hasse");
    }//GEN-LAST:event_jRadioButton7ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        jRadioButton4.setActionCommand("3D");
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jRadioButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton8ActionPerformed
        jRadioButton8.setActionCommand("3D");
    }//GEN-LAST:event_jRadioButton8ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if(jCheckBox1.getActionCommand()==""){
        jCheckBox1.setActionCommand("ok");
        }
        else{
        jCheckBox1.setActionCommand("");    
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
         if(jCheckBox2.getActionCommand()==""){
        jCheckBox2.setActionCommand("ok");
        }
        else{
        jCheckBox2.setActionCommand("");    
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
         if(jCheckBox3.getActionCommand()==""){
        jCheckBox3.setActionCommand("ok");
        }
        else{
        jCheckBox3.setActionCommand("");    
        }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        p = Integer.valueOf(jTextField4.getText());

        int[][] sommets;
        sommets=gener_somm_grille(p);
        double[][] position=new double[sommets.length][2];
        crea_hypergrille(sommets,graph);
        if (jCheckBox4.getActionCommand() == "ok"){
           //traitement des sous graphes
          crea_sous_graphe_g(sommets,graph);
        }
        set_hypergrille(sommets,graph);
        position=set_hypergrille(sommets,graph);
        //traitement des triangles ..
        
        int[][] triangle=new int[p+1][p];
        for(int s=0;s<=p;s++){
           int[][] sommets1;
        sommets1=gener_somm_grille(s); 
          int[] somm_cont=new int[2*s+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        int[] appar = new int[sommets1.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets1.length; i++) {
            
            for (int j = 0; j < sommets1[i].length - 1; j++) {
                if (sommets1[i][j] == 1 && sommets1[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            
            int niv=0;
            if (appar[i] == 1) {
                
                cpt++;
                niv=niveau_grille(sommets1[i]);
                somm_cont[niv]++;

            }
            } 
            triangle[s]=somm_cont;
        }
        //affichage du triangle 
        affich_triangle(triangle);
        
        //creation du graph d'ordre demandé 
        int[] somm_cont=new int[2*p+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            //Node n = graph.getNode(Integer.toString(i + 1));
            for (int j = 0; j < sommets[i].length - 1; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            Node n = graph.getNode(Integer.toString(i + 1));
            int niv=0;
            if (appar[i] == 1) {
                n.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                cpt++;
                niv=niveau_grille(sommets[i]);
                somm_cont[niv]++;

            }
            else{
                n.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre de sommets fibonacci_grille par niveau est :  \n");
        for (int i = 0; i < somm_cont.length; i++) {
            jTextArea1.append(" niveau "+i+" : "+ somm_cont[i] +" \n");
        }
        jTextArea1.append("le nombre de sommets fibonacci_grille total est :"+cpt+" \n");
        try{
        PrintStream pr=new PrintStream(new File("C://Users/ihebmcee/Documents/NetBeansProjects/hypercube/tikzpicturecoloration.txt"));
        System.setOut(pr); 
        int cpt1 = 0;
        for (Edge e : graph.getEachEdge()) {
            int i, t;
            i = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            if (appar[i - 1] == 1 && appar[t - 1] == 1) {
                e.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                pr.append("\\draw[color=red] ("+position[i-1][0]+","+position[i-1][1]+") -- ("+position[t-1][0]+","+position[t-1][1]+");");
                pr.println("");
                cpt1++;
            } else {
                e.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
                pr.append("\\draw[color=gray] ("+position[i-1][0]+","+position[i-1][1]+") -- ("+position[t-1][0]+","+position[t-1][1]+");");
                pr.println("");
            }
        }
        jTextArea1.append("le nombre des arretes contrainte_11 " + cpt1+"\n");
        }catch(FileNotFoundException f){
        System.out.println(f);
        }
        
        
        if (buttonGroup4.getSelection().getActionCommand() == "hasse") {
            Viewer viewer = graph.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        } else {
            Viewer viewer = graph.display();
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        }
        //graph.display();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        p = Integer.valueOf(jTextField4.getText());

        int[][] sommets;
        sommets=gener_somm_grille(p);

        crea_hypergrille(sommets,graph);
        
        if (jCheckBox4.getActionCommand() == "ok"){
           //traitement des sous graphes
        crea_sous_graphe_g(sommets,graph);
        }
        set_hypergrille(sommets,graph);
        
          //traitement des triangles ..
        
        int[][] triangle=new int[p+1][p];
        for(int s=0;s<=p;s++){
           int[][] sommets1;
        sommets1=gener_somm_grille(s); 
          int[] somm_cont=new int[2*s+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        int[] appar = new int[sommets1.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets1.length; i++) {
            
            for (int j = 0; j < sommets1[i].length - 2; j++) {
                if (sommets1[i][j] == 2 && sommets1[i][j + 1] == 2 && sommets1[i][j + 2] == 2) {
                    appar[i] = 0;
                }
            }
            for (int j = 0; j < sommets1[i].length - 1; j++) {
                if (sommets1[i][j] == 1 && sommets1[i][j + 1] == 1 ) {
                    appar[i] = 0;
                }
            }
            
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            
            int niv=0;
            if (appar[i] == 1) {
                
                cpt++;
                niv=niveau_grille(sommets1[i]);
                somm_cont[niv]++;

            }
            } 
            triangle[s]=somm_cont;
        }
        //affichage du triangle 
        affich_triangle(triangle);
        //creation du graph d'ordre demandé 
        
        int[] somm_cont=new int[2*p+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            
            for (int j = 0; j < sommets[i].length - 2; j++) {
                if (sommets[i][j] == 2 && sommets[i][j + 1] == 2 && sommets[i][j + 2] == 2) {
                    appar[i] = 0;
                }
            }
            for (int j = 0; j < sommets[i].length - 1; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1 ) {
                    appar[i] = 0;
                }
            }
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            Node n = graph.getNode(Integer.toString(i + 1));
            int niv=0;
            if (appar[i] == 1) {
                n.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                cpt++;
                niv=niveau_grille(sommets[i]);
                somm_cont[niv]++;
            }
            else{
                n.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre de sommets contrainte 11_222 par niveau est :  \n");
        for (int i = 0; i < somm_cont.length; i++) {
            jTextArea1.append(" niveau "+i+" : "+ somm_cont[i] +" \n");
        }
        jTextArea1.append("le nombre de sommets contrainte 11_222 total est :"+cpt+" \n");
        int cpt1 = 0;
        for (Edge e : graph.getEachEdge()) {
            int i, t;
            i = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            if (appar[i - 1] == 1 && appar[t - 1] == 1) {
                e.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                cpt1++;
            } else {
                e.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre des arretes contrainte_11_222 " + cpt1+"\n");
        set_hypergrille(sommets,graph);
        if (buttonGroup4.getSelection().getActionCommand() == "hasse") {
            Viewer viewer = graph.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        } else {
            Viewer viewer = graph.display();
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph = new SingleGraph("hasse");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
         if(jCheckBox4.getActionCommand()==""){
        jCheckBox4.setActionCommand("ok");
        }
        else{
        jCheckBox4.setActionCommand("");    
        }
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        contrainte_hypergrille c =new contrainte_hypergrille();
        c.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        contrainte_hypercube c =new contrainte_hypercube();
        c.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        p = Integer.valueOf(jTextField1.getText());
        int[][] sommets;
        sommets=gener_somm_cube(p);
        //crea_hypercube(sommets);
        for (int i = 0; i < sommets.length; i++) {
            
            Node n = graph1.addNode(Integer.toString(i + 1));

            String s = "";
            int cpt = 0;
            for (int e : sommets[i]) {
                s += e;
                n.addAttribute("ui.label", s);
            }
        }
        crea_sous_graphe(sommets,graph1);
         set_hypercube(sommets,graph1); 
         //new begin
        
        for (int i = 0; i < sommets.length; ++i){
            int pal_cpt = 0;
            boolean a = false;
            for (int j = 0; j < sommets.length ; ++j){
                if(i==j){
                    j++;
                }
                else{
                int tab1[] = sommets[i];
                int tab2[] = sommets[j];
                if (niveau_cube(tab1) == niveau_cube(tab2)){
                    a = true;
                    if (palindrome(tab1, tab2)){
                        pal_cpt += 1;
                    }
                }
            }
            }   
            if (pal_cpt == 0 &&  (a)){
                Node n1 = graph1.getNode("" + (i + 1));
                double y1 = nodePosition(n1)[1];
                System.out.println("Node : " + (i + 1));
                System.out.println("Before : " + y1);
                if (sommets[i][0]==1){
                //n1.setAttribute("xyz", max/2-0.5, y1 + 0.5, 0);
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 + 0.5, 0);
                }
                else{
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 - 0.5, 0);
                //n1.setAttribute("xyz", max/2-0.5, y1 - 0.5, 0);    
                }
                System.out.println("After : " + nodePosition(n1)[1]);
                      
            
            }    
        }
        // new end
        color_arr_s_graph(graph1); 
        Viewer viewer = graph1.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph1 = new SingleGraph("hasse");
    graph= new SingleGraph("hasse");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        p = Integer.valueOf(jTextField3.getText());
        int[][] sommets;
        sommets=gener_somm_cube(p);
        //crea_hypercube(sommets);
        for (int i = 0; i < sommets.length; i++) {
            
            Node n = graph1.addNode(Integer.toString(i + 1));

            String s = "";
            int cpt = 0;
            for (int e : sommets[i]) {
                s += e;
                n.addAttribute("ui.label", s);
            }
        }
        crea_sous_graphe(sommets,graph1);
         set_hypercube(sommets,graph1);
         
         int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            
            for (int j = 0; j < sommets[i].length - 1; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int c=0;
        
        for (int i = 0; i < appar.length; i++) {
            Node n = graph1.getNode(Integer.toString(i + 1));
            if (appar[i] == 1) {
                
                n.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                c++;
            }
            else{
                n.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre de sommets fibonacci :"+c+" \n");
        //on change les sommets du niveau une seule fois
            int[] deja_change=new int[sommets.length];
            for(int k=0;k<deja_change.length;k++){
                deja_change[k]=0;
            }
        for (Edge e : graph1.getEachEdge()) {
            int i, t;
            
            i = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            
            if((niveau_cube(sommets[i-1])==niveau_cube(sommets[t-1])) && (deja_change[i-1]==0 && deja_change[t-1]==0)){
            Node n1 = graph1.getNode("" + i);
            Node n2 = graph1.getNode("" + t);
            double y1 = nodePosition(n1)[1];
            double y2 = nodePosition(n2)[1];
            n1.setAttribute("xyz", nodePosition(n1)[0], y1 - 0.5, 0);
            deja_change[i-1]=1;
            n2.setAttribute("xyz", nodePosition(n2)[0], y2 + 0.5, 0);
            deja_change[t-1]=1;
            }
            if (appar[i - 1] == 1 && appar[t - 1] == 1) {
                e.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
            } else {
                e.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        
        //new begin
        
        for (int i = 0; i < sommets.length; ++i){
            int pal_cpt = 0;
            boolean a = false;
            for (int j = 0; j < sommets.length ; ++j){
                if(i==j){
                    j++;
                }
                else{
                int tab1[] = sommets[i];
                int tab2[] = sommets[j];
                if (niveau_cube(tab1) == niveau_cube(tab2)){
                    a = true;
                    if (palindrome(tab1, tab2)){
                        pal_cpt += 1;
                    }
                }
            }
            }   
            if (pal_cpt == 0 &&  (a)){
                Node n1 = graph1.getNode("" + (i + 1));
                double y1 = nodePosition(n1)[1];
                System.out.println("Node : " + (i + 1));
                System.out.println("Before : " + y1);
                if (sommets[i][0]==1){
                //n1.setAttribute("xyz", max/2-0.5, y1 + 0.5, 0);
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 + 0.5, 0);
                }
                else{
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 - 0.5, 0);
                //n1.setAttribute("xyz", max/2-0.5, y1 - 0.5, 0);    
                }
                System.out.println("After : " + nodePosition(n1)[1]);
                      
            
            }    
        }
         
        Viewer viewer = graph1.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph1 = new SingleGraph("hasse");
    graph= new SingleGraph("hasse");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        p = Integer.valueOf(jTextField2.getText());
        int[][] sommets;
        sommets=gener_somm_cube(p);
        //crea_hypercube(sommets);
        for (int i = 0; i < sommets.length; i++) {
            
            Node n = graph1.addNode(Integer.toString(i + 1));

            String s = "";
            int cpt = 0;
            for (int e : sommets[i]) {
                s += e;
                n.addAttribute("ui.label", s);
            }
        }
        crea_sous_graphe(sommets,graph1);
         set_hypercube(sommets,graph1);
         
         int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            Node n = graph1.getNode(Integer.toString(i + 1));
            for (int j = 0; j < sommets[i].length - 2; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1 && sommets[i][j + 2] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int c=0;
        for (int i = 0; i < appar.length; i++) {
            Node n = graph1.getNode(Integer.toString(i + 1));
            if (appar[i] == 1) {
                n.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                c++;
            }
            else{
                n.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre de sommets fibonacci :"+c+" \n");
        for (Edge e : graph1.getEachEdge()) {
            int i, t;
            i = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            if (appar[i - 1] == 1 && appar[t - 1] == 1) {
                e.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
            } else {
                e.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        
        //new begin
        
        for (int i = 0; i < sommets.length; ++i){
            int pal_cpt = 0;
            boolean a = false;
            for (int j = 0; j < sommets.length ; ++j){
                if(i==j){
                    j++;
                }
                else{
                int tab1[] = sommets[i];
                int tab2[] = sommets[j];
                if (niveau_cube(tab1) == niveau_cube(tab2)){
                    a = true;
                    if (palindrome(tab1, tab2)){
                        pal_cpt += 1;
                    }
                }
            }
            }   
            if (pal_cpt == 0 &&  (a)){
                Node n1 = graph1.getNode("" + (i + 1));
                double y1 = nodePosition(n1)[1];
                System.out.println("Node : " + (i + 1));
                System.out.println("Before : " + y1);
                if (sommets[i][0]==1){
                //n1.setAttribute("xyz", max/2-0.5, y1 + 0.5, 0);
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 + 0.5, 0);
                }
                else{
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 - 0.5, 0);
                //n1.setAttribute("xyz", max/2-0.5, y1 - 0.5, 0);    
                }
                System.out.println("After : " + nodePosition(n1)[1]);
                      
            
            }    
        }
         
        Viewer viewer = graph1.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph1 = new SingleGraph("hasse");
    graph= new SingleGraph("hasse");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        p = Integer.valueOf(jTextField4.getText());
        int[][] sommets;
        sommets=gener_somm_grille(p);
        //crea_hypercube(sommets);
        for (int i = 0; i < sommets.length; i++) {
            
            Node n = graph1.addNode(Integer.toString(i + 1));

            String s = "";
            int cpt = 0;
            for (int e : sommets[i]) {
                s += e;
                if (e == 1) {
                    cpt++;
                }
                n.addAttribute("ui.label", s);
            }
        }
        crea_sous_graphe_g(sommets,graph1);
         set_hypergrille(sommets,graph1);
         
         
         //new begin
        
        for (int i = 0; i < sommets.length; ++i){
            int pal_cpt = 0;
            boolean a = false;
            for (int j = 0; j < sommets.length ; ++j){
                if(i==j){
                    j++;
                }
                else{
                int tab1[] = sommets[i];
                int tab2[] = sommets[j];
                if (niveau_grille(tab1) == niveau_grille(tab2)){
                    a = true;
                    if (palindrome(tab1, tab2)){
                        pal_cpt += 1;
                    }
                }
            }
            }   
            if (pal_cpt == 0 &&  (a)){
                Node n1 = graph1.getNode("" + (i + 1));
                double y1 = nodePosition(n1)[1];
                System.out.println("Node : " + (i + 1));
                System.out.println("Before : " + y1);
                if (sommets[i][0]==1){
                //n1.setAttribute("xyz", max/2-0.5, y1 + 0.5, 0);
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 + 0.5, 0);
                }
                else{
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 - 0.5, 0);
                //n1.setAttribute("xyz", max/2-0.5, y1 - 0.5, 0);    
                }
                System.out.println("After : " + nodePosition(n1)[1]);
                      
            
            }    
        }
         
        Viewer viewer = graph1.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph1 = new SingleGraph("hasse");
    graph= new SingleGraph("hasse");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        p = Integer.valueOf(jTextField4.getText());
        int[][] sommets;
        sommets=gener_somm_grille(p);
        //crea_hypercube(sommets);
        for (int i = 0; i < sommets.length; i++) {
            
            Node n = graph1.addNode(Integer.toString(i + 1));

            String s = "";
            int cpt = 0;
            for (int e : sommets[i]) {
                s += e;
                if (e == 1) {
                    cpt++;
                }
                n.addAttribute("ui.label", s);
            }
        }
        crea_sous_graphe_g(sommets,graph1);
         set_hypergrille(sommets,graph1);
         
         int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            //Node n = graph.getNode(Integer.toString(i + 1));
            for (int j = 0; j < sommets[i].length - 1; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            Node n = graph1.getNode(Integer.toString(i + 1));

            if (appar[i] == 1) {
                n.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                cpt++;

            }
            else{
                n.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre de sommets contrainte_11 " + cpt+"\n");
        int cpt1 = 0;
        for (Edge e : graph1.getEachEdge()) {
            int i, t;
            i = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            if (appar[i - 1] == 1 && appar[t - 1] == 1) {
                e.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                cpt1++;
            } else {
                e.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre des arretes contrainte_11 " + cpt1+"\n");
        
        //new begin
        
        for (int i = 0; i < sommets.length; ++i){
            int pal_cpt = 0;
            boolean a = false;
            for (int j = 0; j < sommets.length ; ++j){
                if(i==j){
                    j++;
                }
                else{
                int tab1[] = sommets[i];
                int tab2[] = sommets[j];
                if (niveau_grille(tab1) == niveau_grille(tab2)){
                    a = true;
                    if (palindrome(tab1, tab2)){
                        pal_cpt += 1;
                    }
                }
            }
            }   
            if (pal_cpt == 0 &&  (a)){
                Node n1 = graph1.getNode("" + (i + 1));
                double y1 = nodePosition(n1)[1];
                System.out.println("Node : " + (i + 1));
                System.out.println("Before : " + y1);
                if (sommets[i][0]==1){
                //n1.setAttribute("xyz", max/2-0.5, y1 + 0.5, 0);
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 + 0.5, 0);
                }
                else{
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 - 0.5, 0);
                //n1.setAttribute("xyz", max/2-0.5, y1 - 0.5, 0);    
                }
                System.out.println("After : " + nodePosition(n1)[1]);
                      
            
            }    
        }
        
         
        Viewer viewer = graph1.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph1 = new SingleGraph("hasse");
    graph= new SingleGraph("hasse");
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        p = Integer.valueOf(jTextField4.getText());
        int[][] sommets;
        sommets=gener_somm_grille(p);
        //crea_hypercube(sommets);
        for (int i = 0; i < sommets.length; i++) {
            
            Node n = graph1.addNode(Integer.toString(i + 1));

            String s = "";
            int cpt = 0;
            for (int e : sommets[i]) {
                s += e;
                if (e == 1) {
                    cpt++;
                }
                n.addAttribute("ui.label", s);
            }
        }
        crea_sous_graphe_g(sommets,graph1);
         set_hypergrille(sommets,graph1);
         
         int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            //Node n = graph.getNode(Integer.toString(i + 1));
            for (int j = 0; j < sommets[i].length - 2; j++) {
                if ((sommets[i][j] == 1 && sommets[i][j + 1] == 1) || (sommets[i][j] == 2 && sommets[i][j + 1] == 2 && sommets[i][j + 2] == 2)) {
                    appar[i] = 0;
                }
            }
            if (sommets[i][sommets[i].length - 2] == 1 && sommets[i][sommets[i].length -1 ] == 1) {
                appar[i] = 0;
            }
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            Node n = graph1.getNode(Integer.toString(i + 1));

            if (appar[i] == 1) {
                n.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                cpt++;

            }
            else{
                n.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre de sommets contrainte_11 " + cpt+"\n");
        int cpt1 = 0;
        for (Edge e : graph1.getEachEdge()) {
            int i, t;
            i = Integer.valueOf(e.getSourceNode().getId());
            t = Integer.valueOf(e.getTargetNode().getId());
            if (appar[i - 1] == 1 && appar[t - 1] == 1) {
                e.addAttribute("ui.style", "fill-color: rgb(255,99,71);");
                cpt1++;
            } else {
                e.addAttribute("ui.style", "fill-color: rgb(212, 212, 212);");
            }
        }
        jTextArea1.append("le nombre des arretes contrainte_11 " + cpt1+"\n");
        
        //new begin
        
        for (int i = 0; i < sommets.length; ++i){
            int pal_cpt = 0;
            boolean a = false;
            for (int j = 0; j < sommets.length ; ++j){
                if(i==j){
                    j++;
                }
                else{
                int tab1[] = sommets[i];
                int tab2[] = sommets[j];
                if (niveau_grille(tab1) == niveau_grille(tab2)){
                    a = true;
                    if (palindrome(tab1, tab2)){
                        pal_cpt += 1;
                    }
                }
            }
            }   
            if (pal_cpt == 0 &&  (a)){
                Node n1 = graph1.getNode("" + (i + 1));
                double y1 = nodePosition(n1)[1];
                System.out.println("Node : " + (i + 1));
                System.out.println("Before : " + y1);
                if (sommets[i][0]==1){
                //n1.setAttribute("xyz", max/2-0.5, y1 + 0.5, 0);
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 + 0.5, 0);
                }
                else{
                n1.setAttribute("xyz", nodePosition(n1)[0], y1 - 0.5, 0);
                //n1.setAttribute("xyz", max/2-0.5, y1 - 0.5, 0);    
                }
                System.out.println("After : " + nodePosition(n1)[1]);
                      
            
            }    
        }
         
        Viewer viewer = graph1.display(false);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    graph1 = new SingleGraph("hasse");
    graph= new SingleGraph("hasse");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        p = Integer.valueOf(jTextField3.getText());
        //traitement ...
        int[][] triangle=new int[p+1][p];
         
        for(int s=0;s<=p;s++){
            int[][] sommets1;
        sommets1=gener_somm_cube(s);
          
         int[] appar = new int[sommets1.length];
        int[] somm_cont=new int[s+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets1.length; i++) {
            
            for (int j = 0; j < sommets1[i].length - 1; j++) {
                if (sommets1[i][j] == 1 && sommets1[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int c=0,niv;
        for (int i = 0; i < appar.length; i++) {
            niv=0;
            if (appar[i] == 1) {
                c++;
                niv=niveau_cube(sommets1[i]);
                somm_cont[niv]++;
            }    
        }
             triangle[s]=somm_cont;  
        }
        //affichage du triangle
        affich_triangle(triangle);
        export_txt_c(triangle);
        JOptionPane.showMessageDialog(null, "résultats exportés");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        p = Integer.valueOf(jTextField2.getText());
        //traitement ..
        int[][] triangle=new int[p+1][p];
         
        for(int s=0;s<=p;s++){
            int[][] sommets1;
        sommets1=gener_somm_cube(s);
        int[] somm_cont=new int[s+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        int[] appar = new int[sommets1.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets1.length; i++) {
            
            for (int j = 0; j < sommets1[i].length - 2; j++) {
                if (sommets1[i][j] == 1 && sommets1[i][j + 1] == 1 && sommets1[i][j + 2] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            int niv=0;
            
            if (appar[i] == 1) {
                
                cpt++;
                niv=niveau_cube(sommets1[i]);
                somm_cont[niv]++;
            }
            
        }
        triangle[s]=somm_cont;
        
        }
        //affichage du triangle
        affich_triangle(triangle);
        export_txt_c(triangle);
        JOptionPane.showMessageDialog(null, "résultats exportés");
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        p = Integer.valueOf(jTextField4.getText());
        //traitement des triangles ..
        
        int[][] triangle=new int[p+1][p];
        for(int s=0;s<=p;s++){
           int[][] sommets1;
        sommets1=gener_somm_grille(s); 
          int[] somm_cont=new int[2*s+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        int[] appar = new int[sommets1.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets1.length; i++) {
            
            for (int j = 0; j < sommets1[i].length - 1; j++) {
                if (sommets1[i][j] == 1 && sommets1[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            
            int niv=0;
            if (appar[i] == 1) {
                
                cpt++;
                niv=niveau_grille(sommets1[i]);
                somm_cont[niv]++;

            }
            } 
            triangle[s]=somm_cont;
        }
        //affichage du triangle 
        affich_triangle(triangle);
        export_txt_g(triangle);
        JOptionPane.showMessageDialog(null, "résultats exportés");
        
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        p = Integer.valueOf(jTextField4.getText());
        
        //traitement des triangles ..
        
        int[][] triangle=new int[p+1][p];
        for(int s=0;s<=p;s++){
           int[][] sommets1;
        sommets1=gener_somm_grille(s); 
          int[] somm_cont=new int[2*s+1];
        for (int i = 0; i < somm_cont.length; i++) {
            somm_cont[i] = 0;
        }
        int[] appar = new int[sommets1.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets1.length; i++) {
            
            for (int j = 0; j < sommets1[i].length - 2; j++) {
                if (sommets1[i][j] == 2 && sommets1[i][j + 1] == 2 && sommets1[i][j + 2] == 2) {
                    appar[i] = 0;
                }
            }
            for (int j = 0; j < sommets1[i].length - 1; j++) {
                if (sommets1[i][j] == 1 && sommets1[i][j + 1] == 1 ) {
                    appar[i] = 0;
                }
            }
            
        }
        int cpt = 0;
        for (int i = 0; i < appar.length; i++) {
            
            int niv=0;
            if (appar[i] == 1) {
                
                cpt++;
                niv=niveau_grille(sommets1[i]);
                somm_cont[niv]++;

            }
            } 
            triangle[s]=somm_cont;
        }
        //affichage du triangle 
        affich_triangle(triangle);
        
        export_txt_g(triangle);
        JOptionPane.showMessageDialog(null, "résultats exportés");
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        int[][] sommets;
        p = Integer.valueOf(jTextField1.getText());
        sommets=gener_somm_cube(p);
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        int det;
        det=hyperdeterminant(sommets,appar);
        jTextField5.setText(""+det);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        p = Integer.valueOf(jTextField3.getText());
        int det;
        int[][] sommets;
        sommets=gener_somm_cube(p);
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            for (int j = 0; j < sommets[i].length - 1; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        det=hyperdeterminant(sommets,appar);
        jTextField5.setText(""+det);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        p = Integer.valueOf(jTextField2.getText());
        int det;
        int[][] sommets;
        sommets=gener_somm_cube(p);
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            
            for (int j = 0; j < sommets[i].length - 2; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1 && sommets[i][j + 2] == 1) {
                    appar[i] = 0;
                }
            }
        }
        det=hyperdeterminant(sommets,appar);
        jTextField5.setText(""+det);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
         p = Integer.valueOf(jTextField4.getText());
         int det=0;
        int[][] sommets;
        sommets=gener_somm_grille(p);
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        det=hyperdeterminant_g(sommets,appar,p);
        jTextField5.setText(""+det);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        p = Integer.valueOf(jTextField4.getText());
         int det=0;
        int[][] sommets;
        sommets=gener_somm_grille(p);
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            //Node n = graph.getNode(Integer.toString(i + 1));
            for (int j = 0; j < sommets[i].length - 1; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1) {
                    appar[i] = 0;
                }
            }
        }
        det=hyperdeterminant_g(sommets,appar,p);
        jTextField5.setText(""+det);
        
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        p = Integer.valueOf(jTextField4.getText());
         int det=0;
        int[][] sommets;
        sommets=gener_somm_grille(p);
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        for (int i = 0; i < sommets.length; i++) {
            
            for (int j = 0; j < sommets[i].length - 2; j++) {
                if (sommets[i][j] == 2 && sommets[i][j + 1] == 2 && sommets[i][j + 2] == 2) {
                    appar[i] = 0;
                }
            }
            for (int j = 0; j < sommets[i].length - 1; j++) {
                if (sommets[i][j] == 1 && sommets[i][j + 1] == 1 ) {
                    appar[i] = 0;
                }
            }
        }
        det=hyperdeterminant_g(sommets,appar,p);
        jTextField5.setText(""+det);
        
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        triangle_hyper();
    }//GEN-LAST:event_jButton25ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main_menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main_menu().setVisible(true);
                
            }
        });
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTextArea jTextArea1;
    public static javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    public static javax.swing.JTextField jTextField6;
    public static javax.swing.JTextField jTextField7;
    public static javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
