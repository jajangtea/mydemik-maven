/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.mydemik.com;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author jajangtea
 */
public class MenuUtama extends javax.swing.JFrame {
    private final JPanel contentPane;
	private final JLabel lblWall;
	int x;
	int y;
	boolean mousePress;
	private final JLabel lbl;
	private final JLabel lblExit;
	private final JLabel label;
	private final JLabel label_1;
	private final JLabel label_2;
	private final JLabel label_3;
	private final JLabel label_4;

    /**
     * Creates new form MenuUtama
     */
    public MenuUtama() {
        addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				if (mousePress = true) {
					int tX = MenuUtama.this.getLocation().x + me.getX();
					int tY = MenuUtama.this.getLocation().y + me.getY();

					int X = tX - x;
					int Y = tY - y;

					MenuUtama.this.setLocation(X, Y);
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				mousePress = true;
				x = me.getX();
				y = me.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				mousePress = false;
			}
		});
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lbl = new JLabel("");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		//lbl.setFont(new Font("Bauhaus Hv BT", Font.BOLD, 20));
		lbl.setBounds(160, 172, 70, 65);
                lbl.setIcon(new ImageIcon(MenuUtama.class
				.getResource("/images/stti.png")));
                //lbl.setBounds(104, 249, 70, 65);
		contentPane.add(lbl);

		lblExit = new JLabel("");
		lblExit.setToolTipText("Exit");
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		lblExit.setIcon(new ImageIcon(MenuUtama.class
				.getResource("/images/exit.png")));
		lblExit.setBounds(104, 249, 70, 65);
		contentPane.add(lblExit);

		label = new JLabel("");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                            try 
                            {
                                 __EntryPerusahaan fd;
                                fd = new __EntryPerusahaan();
                                fd.setVisible(true);
                            } catch (SQLException ex) {
                                Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		});
		label.setToolTipText("Perusahaan");
		label.setIcon(new ImageIcon(MenuUtama.class
				.getResource("/images/help.png")));
		label.setBounds(325, 172, 70, 65);
		contentPane.add(label);

		label_1 = new JLabel("");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try 
                {
                    __EntryTA fp;
                    fp = new __EntryTA();
                    fp.setVisible(rootPaneCheckingEnabled);
                } catch (SQLException ex) {
                    Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, ex);
                }
			}
		});
		label_1.setToolTipText("Tahun Ajaran");
		label_1.setIcon(new ImageIcon(MenuUtama.class
				.getResource("/images/ta.png")));
		label_1.setBounds(10, 172, 70, 65);
		contentPane.add(label_1);

		label_2 = new JLabel("");
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try 
                {
                    __EntryProdi ep = new __EntryProdi();
                    ep.setVisible(true);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, ex);
                } 
			}
		});
		label_2.setToolTipText("Prodi");
		label_2.setIcon(new ImageIcon(MenuUtama.class
				.getResource("/images/prodi.png")));
		label_2.setBounds(224, 249, 70, 65);
		contentPane.add(label_2);

		label_3 = new JLabel("");
		label_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try {
                    __EntryMahasiswa fp;
                    fp = new __EntryMahasiswa();
                    fp.setVisible(rootPaneCheckingEnabled);
                } catch (SQLException ex) {
                    Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, ex);
                }
			}
		});
		label_3.setToolTipText("Mahasiswa");
		label_3.setIcon(new ImageIcon(MenuUtama.class
				.getResource("/images/compose.png")));
		label_3.setBounds(104, 91, 70, 65);
		contentPane.add(label_3);

		label_4 = new JLabel("");
		label_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                            try 
                            {
                                FSurat fsurat;
                                fsurat = new FSurat();
                                fsurat.setVisible(rootPaneCheckingEnabled);
                            } catch (SQLException ex) {
                                Logger.getLogger(MenuUtama.class.getName()).log(Level.SEVERE, null, ex);
                            }
                           
			}
		});
		label_4.setToolTipText("Data Surat");
		label_4.setIcon(new ImageIcon(MenuUtama.class
				.getResource("/images/document.png")));
		label_4.setBounds(224, 91, 70, 65);
		contentPane.add(label_4);

		lblWall = new JLabel("");
		lblWall.setIcon(new ImageIcon(MenuUtama.class
				.getResource("/images/wall.jpg")));
		lblWall.setBounds(0, 0, 413, 407);
		contentPane.add(lblWall);
		setShape(new Ellipse2D.Double(0, 0, 400, 400));
		setLocationRelativeTo(null);
                this.setTitle("Menu Utama");
                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
