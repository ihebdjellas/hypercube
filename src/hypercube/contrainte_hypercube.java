
package hypercube;
import java.util.ArrayList;
import java.util.Scanner;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import static org.graphstream.algorithm.Toolkit.*;
import org.graphstream.ui.view.Viewer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import javax.swing.JOptionPane;
/**
 *
 * @author ihebmcee
 */
public class contrainte_hypercube extends javax.swing.JFrame {

    public static Graph graph = new SingleGraph("hasse");
    public static Graph graph1 = new SingleGraph("hypercube");
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
        main_menu.jTextArea1.append("\n");
      return sommets;  
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

            //n.addAttribute("ui.style", "fill-color: rgb(255, 0, 0);");
        }
        /*for (int i = 0; i < a.length; i++) {
         Node n1 = graph.getNode(Integer.toString(i + 1));
         n1.setAttribute("ui.x", i);
         n1.setAttribute("ui.y", 1);
         n1.setAttribute("ui.z", 0);
         }*/

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
                    if (a[i][j] != a[i1][j]) {
                        dif += 1;
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
        PrintStream p=new PrintStream(new File("C://Users/ihebmcee/Documents/NetBeansProjects/hypercube/hypercube_triangle.txt"));
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
    

    public contrainte_hypercube() {
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
        jRadioButton2 = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("3D");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
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

        jButton1.setText("Hypercube");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Dimension :");

        jLabel2.setText("Suites à éliminer :");

        jLabel1.setFont(new java.awt.Font("Lucida Calligraphy", 0, 14)); // NOI18N
        jLabel1.setText("Hypercubes Personnalisés");

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
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(33, 33, 33)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField5)))))))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jButton4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jCheckBox1)))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    /*
     * @param java.awt.event.actionEvent event on click
     * @return int[][]
     */
    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        jRadioButton1.setActionCommand("hasse");
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String chaine = String.valueOf(jTextField1.getText());
        String chaine1 = String.valueOf(jTextField3.getText());
        String chaine2 = String.valueOf(jTextField4.getText());
        p = Integer.valueOf(jTextField2.getText());
        int[][] sommets;
        sommets=gener_somm_cube(p);
        double[][] position=new double[sommets.length][2];
        crea_hypercube(sommets,graph);
        if (jCheckBox1.getActionCommand() == "ok") {
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
        int k;
        for (int i = 0; i < sommets1.length; i++) {
            
            
        
            
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
            for (int j = 0; j < sommets1[i].length-chaine1.length() + 1; j++){
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
            for (int j = 0; j < sommets1[i].length-chaine2.length() + 1; j++){
                k = 0; 
              
               while(k < chaine2.length()){
                   
                   if (sommets1[i][j + k] == Integer.parseInt(chaine2.charAt(k) + "") ) {
                        
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
        
        //verification des contraintes
        int[] somm_cont=new int[p+1];
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
            Node n = graph.getNode(Integer.toString(i + 1));
            int niv=0;
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
        main_menu.jTextArea1.append("le nombre de sommets contraintes par niveau est :  \n");
        for (int i = 0; i < somm_cont.length; i++) {
            main_menu.jTextArea1.append(" niveau "+i+" : "+ somm_cont[i] +" \n");
        }
        main_menu.jTextArea1.append("le nombre de sommets contraintes total est :"+cpt+" \n");
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
        main_menu.jTextArea1.append("le nombre des arretes contrainte " + cpt1+"\n");
        }catch(FileNotFoundException f){
        System.out.println(f);
        }
        
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String chaine = String.valueOf(jTextField1.getText());
        String chaine1 = String.valueOf(jTextField3.getText());
        String chaine2 = String.valueOf(jTextField4.getText());
        p = Integer.valueOf(jTextField2.getText());
        int[][] sommets;
        sommets=gener_somm_cube(p);
        
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
        crea_sous_graphe(sommets,graph1);
         set_hypercube(sommets,graph1);
         
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String chaine = String.valueOf(jTextField1.getText());
        String chaine1 = String.valueOf(jTextField3.getText());
        String chaine2 = String.valueOf(jTextField4.getText());
        p = Integer.valueOf(jTextField2.getText());
        
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
        int k;
        for (int i = 0; i < sommets1.length; i++) {
            
            
        
            
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
            for (int j = 0; j < sommets1[i].length-chaine1.length() + 1; j++){
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
            for (int j = 0; j < sommets1[i].length-chaine2.length() + 1; j++){
                k = 0; 
              
               while(k < chaine2.length()){
                   
                   if (sommets1[i][j + k] == Integer.parseInt(chaine2.charAt(k) + "") ) {
                        
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
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String chaine = String.valueOf(jTextField1.getText());
        String chaine1 = String.valueOf(jTextField3.getText());
        String chaine2 = String.valueOf(jTextField4.getText());
        p = Integer.valueOf(jTextField2.getText());
        int det;
        int[][] sommets;
        sommets=gener_somm_cube(p);
        int[] appar = new int[sommets.length];
        for (int i = 0; i < appar.length; i++) {
            appar[i] = 1;
        }
        int k;
        for (int i = 0; i < sommets.length; i++) {
            
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
        det=hyperdeterminant(sommets,appar);
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
            java.util.logging.Logger.getLogger(contrainte_hypercube.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(contrainte_hypercube.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(contrainte_hypercube.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(contrainte_hypercube.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new contrainte_hypercube().setVisible(true);
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
