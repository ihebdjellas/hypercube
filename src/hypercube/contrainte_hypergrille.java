package hypercube;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import static org.graphstream.algorithm.Toolkit.*;
import org.graphstream.ui.view.Viewer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ihebmcee
 */
public class contrainte_hypergrille extends javax.swing.JFrame {

    public static Graph graph = new SingleGraph("hasse");
    public static Graph graph1 = new SingleGraph("hypercube");
    public static int p;
     public static int niveau_grille(int[] code) {
        int niv = 0;

        for (int e : code) {

            niv = niv + e;

        }
        return niv;
    }
     
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

    public static void set_hypergrille(int[][] a,Graph gg) {
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
                    
                } else {
                    if(k==0){
                      double f = ((double)(max-som_niv[niv]) / 2  );
                    n.setAttribute("xyz", f, niv * 2, 0);
                    pos_atte[niv]=f;
                    }
                    else{
                      //double f = (pos_atte[niv] + (double)((diff[niv]/kk)) );
                        double f = (pos_atte[niv] + 1 );
                    n.setAttribute("xyz", f, niv * 2, 0);
                    pos_atte[niv]=f;
                    }
                    

                }

                //System.out.println("  k1  " + i + " " + k);
            } else {
                
                 n.setAttribute("xyz", k, niv * 2, 0);
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
        //graph.display(false);
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
    
    //fonction pour afficher le triangle 
    public static void affich_triangle(int[][] triangle){
     jTextArea1.setText("");   
    jTextArea1.append(" le triangle associé est : \n");
        for (int i = 0; i < triangle.length; i++){
            jTextArea1.append("\n");
            for (int j = 0; j < triangle[i].length; j++){
               jTextArea1.append( triangle[i][j] +"  "); 
            }
        }
        jTextArea1.append("\n");    
    }
    
    //fonction pour exporter les triangles hypergrilles dans un fichier text
    public static void export_txt_g(int[][]a){
        try{
        PrintStream p=new PrintStream(new File("C://Users/ihebmcee/Documents/NetBeansProjects/hypercube/hypergrille_triangle.txt"));
        System.setOut(p);
        p.println("le triangle obtenu :");
        for(int i=0;i<a.length;i++){
            p.println("");
            for(int j=0;j<a[i].length;j++){
                p.print(" "+a[i][j]);
            }
        }
        
        
    }catch(FileNotFoundException f){
        System.out.println(f);
    }
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
   
    
    public contrainte_hypergrille() {
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();

        setTitle("contraintes hypergrille");

        jLabel1.setFont(new java.awt.Font("Lucida Calligraphy", 0, 14)); // NOI18N
        jLabel1.setText("Hypergrilles Personnalisées");

        jLabel2.setText("Suites à eliminer :");

        jButton1.setText("Hypergrille");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Arretes Sous Graphes");
        jCheckBox1.setActionCommand("");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
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
        jRadioButton2.setText("3D");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Dimension :");

        jButton2.setText("Sous Graphes");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel4.setText("Triangle :");

        jButton3.setText("Triangle");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Hyperdeterminant");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jRadioButton1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(jCheckBox1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(40, 40, 40)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                            .addComponent(jTextField5))))))
                        .addGap(0, 20, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(60, 60, 60)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jCheckBox1))))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String chaine = String.valueOf(jTextField1.getText());
        String chaine1 = String.valueOf(jTextField3.getText());
        String chaine2 = String.valueOf(jTextField4.getText());
        p = Integer.valueOf(jTextField2.getText());
        int[][] sommets;
        sommets=gener_somm_grille(p);
        crea_hypergrille(sommets,graph);
        if (jCheckBox1.getActionCommand() == "ok"){
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
        int k;
        
        for (int i = 0; i < sommets1.length; i++) {
            //Node n = graph.getNode(Integer.toString(i + 1));
            if(String.valueOf(jTextField1.getText()).length()>0){
            for (int j = 0; j < sommets1[i].length-chaine.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine.length()){
                   
                   if (sommets1[i][j + k] == Integer.parseInt(chaine.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine.length()){
                    appar[i] = 0;
                }
            }
            }
            if(String.valueOf(jTextField3.getText()).length()>0){
            for (int j = 0; j < sommets1[i].length-chaine1.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine1.length()){
                   
                   if (sommets1[i][j + k] == Integer.parseInt(chaine1.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine1.length()){
                    appar[i] = 0;
                }
            }
            }
            
            if(String.valueOf(jTextField4.getText()).length()>0){
            for (int j = 0; j < sommets1[i].length-chaine2.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine2.length()){
                   
                   if (sommets1[i][j + k] == Integer.parseInt(chaine2.charAt(k)+"" ) ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine2.length()){
                    appar[i] = 0;
                }
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
        int k;
        
        for (int i = 0; i < sommets.length; i++) {
            //Node n = graph.getNode(Integer.toString(i + 1));
            if(String.valueOf(jTextField1.getText()).length()>0){
            for (int j = 0; j < sommets[i].length-chaine.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine.length()){
                   
                   if (sommets[i][j + k] == Integer.parseInt(chaine.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine.length()){
                    appar[i] = 0;
                }
            }
            }
            if(String.valueOf(jTextField3.getText()).length()>0){
            for (int j = 0; j < sommets[i].length-chaine1.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine1.length()){
                   
                   if (sommets[i][j + k] == Integer.parseInt(chaine1.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine1.length()){
                    appar[i] = 0;
                }
            }
            }
            
            if(String.valueOf(jTextField4.getText()).length()>0){
            for (int j = 0; j < sommets[i].length-chaine2.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine2.length()){
                   
                   if (sommets[i][j + k] == Integer.parseInt(chaine2.charAt(k)+"" ) ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine2.length()){
                    appar[i] = 0;
                }
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
        main_menu.jTextArea1.append("le nombre de sommets contraintes par niveau est :  \n");
        for (int i = 0; i < somm_cont.length; i++) {
            main_menu.jTextArea1.append(" niveau "+i+" : "+ somm_cont[i] +" \n");
        }
        main_menu.jTextArea1.append("le nombre de sommets contraintes total est :"+cpt+" \n");
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
        main_menu.jTextArea1.append("le nombre des arretes contrainte " + cpt1+"\n");
        set_hypergrille(sommets,graph);
        if (buttonGroup1.getSelection().getActionCommand() == "hasse") {
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        jRadioButton1.setActionCommand("hasse");
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        jRadioButton2.setActionCommand("3D");
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
         if(jCheckBox1.getActionCommand()==""){
        jCheckBox1.setActionCommand("ok");
        }
        else{
        jCheckBox1.setActionCommand("");    
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String chaine = String.valueOf(jTextField1.getText());
        String chaine1 = String.valueOf(jTextField3.getText());
        String chaine2 = String.valueOf(jTextField4.getText());
        p = Integer.valueOf(jTextField2.getText());
        int[][] sommets;
        sommets=gener_somm_grille(p);
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
        
        int k;
        for (int i = 0; i < sommets.length; i++) {
            //Node n = graph.getNode(Integer.toString(i + 1));
            if(String.valueOf(jTextField1.getText()).length()>0){
            for (int j = 0; j < sommets[i].length-chaine.length() + 1; j++) {
               
               k = 0; 
              
               while(k < chaine.length()){
                   
                   if (sommets[i][j + k] == Integer.parseInt(chaine.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
               
                if(k == chaine.length()){
                    appar[i] = 0;
                }
                
            }
            }
            if(String.valueOf(jTextField3.getText()).length()>0){
            for (int j = 0; j < sommets[i].length-chaine1.length() + 1; j++){
                k = 0; 
              
               while(k < chaine1.length()){
                   
                   if (sommets[i][j + k] == Integer.parseInt(chaine1.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
               
                if(k == chaine1.length()){
                    appar[i] = 0;
                }
            }
            }
            if(String.valueOf(jTextField4.getText()).length()>0){
            for (int j = 0; j < sommets[i].length-chaine2.length() + 1; j++){
                k = 0; 
              
               while(k < chaine2.length()){
                   
                   if (sommets[i][j + k] == Integer.parseInt(chaine2.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
               
                if(k == chaine2.length()){
                    appar[i] = 0;
                }
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
        main_menu.jTextArea1.append("le nombre de sommets contrainte  " + cpt+"\n");
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
        main_menu.jTextArea1.append("le nombre des arretes contrainte " + cpt1+"\n");
        
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String chaine = String.valueOf(jTextField1.getText());
        String chaine1 = String.valueOf(jTextField3.getText());
        String chaine2 = String.valueOf(jTextField4.getText());
        p = Integer.valueOf(jTextField2.getText());
        
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
        int k;
        
        for (int i = 0; i < sommets1.length; i++) {
            //Node n = graph.getNode(Integer.toString(i + 1));
            if(String.valueOf(jTextField1.getText()).length()>0){
            for (int j = 0; j < sommets1[i].length-chaine.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine.length()){
                   
                   if (sommets1[i][j + k] == Integer.parseInt(chaine.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine.length()){
                    appar[i] = 0;
                }
            }
            }
            if(String.valueOf(jTextField3.getText()).length()>0){
            for (int j = 0; j < sommets1[i].length-chaine1.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine1.length()){
                   
                   if (sommets1[i][j + k] == Integer.parseInt(chaine1.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine1.length()){
                    appar[i] = 0;
                }
            }
            }
            
            if(String.valueOf(jTextField4.getText()).length()>0){
            for (int j = 0; j < sommets1[i].length-chaine2.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine2.length()){
                   
                   if (sommets1[i][j + k] == Integer.parseInt(chaine2.charAt(k)+"" ) ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine2.length()){
                    appar[i] = 0;
                }
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
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String chaine = String.valueOf(jTextField1.getText());
        String chaine1 = String.valueOf(jTextField3.getText());
        String chaine2 = String.valueOf(jTextField4.getText());
        p = Integer.valueOf(jTextField2.getText());
         int det=0;
        int[][] sommets;
        sommets=gener_somm_grille(p);
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        int k;
        
        for (int i = 0; i < sommets.length; i++) {
            //Node n = graph.getNode(Integer.toString(i + 1));
            if(String.valueOf(jTextField1.getText()).length()>0){
            for (int j = 0; j < sommets[i].length-chaine.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine.length()){
                   
                   if (sommets[i][j + k] == Integer.parseInt(chaine.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine.length()){
                    appar[i] = 0;
                }
            }
            }
            if(String.valueOf(jTextField3.getText()).length()>0){
            for (int j = 0; j < sommets[i].length-chaine1.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine1.length()){
                   
                   if (sommets[i][j + k] == Integer.parseInt(chaine1.charAt(k) + "") ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine1.length()){
                    appar[i] = 0;
                }
            }
            }
            
            if(String.valueOf(jTextField4.getText()).length()>0){
            for (int j = 0; j < sommets[i].length-chaine2.length() + 1; j++) {
               
               k = 0; 
               while(k < chaine2.length()){
                   
                   if (sommets[i][j + k] == Integer.parseInt(chaine2.charAt(k)+"" ) ) {
                        
                        k++;
                    }
                    else{
                         break;
                    }
               }
                if(k == chaine2.length()){
                    appar[i] = 0;
                }
            }
            }
        }
        det=hyperdeterminant_g(sommets,appar,p);
        jTextField5.setText(""+det);
        
    
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(contrainte_hypergrille.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(contrainte_hypergrille.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(contrainte_hypergrille.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(contrainte_hypergrille.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new contrainte_hypergrille().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
