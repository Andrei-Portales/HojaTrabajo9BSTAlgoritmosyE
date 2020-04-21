import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

public class Main {

	private JFrame frame;
	private JPanel Factory, Inicio;
	private JRadioButton rdRedBlack,rdSplay,rdHash;
	private Controlador control;
	private JButton btnNewButton;
	private JPanel traduccion;
	private JTextArea txtTexto,txtTraduccion;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JLabel lblT;
	private JLabel tblTD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.setBounds(100, 100, 658, 733);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		Factory = new JPanel();
		Factory.setBackground(Color.WHITE);
		frame.getContentPane().add(Factory, "name_96924720452600");
		Factory.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(49, 149, 539, 401);
		Factory.add(panel);
		panel.setLayout(null);
		
		rdRedBlack = new JRadioButton("RedBlackTree Implementation");
		rdRedBlack.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (rdRedBlack.isSelected()) {
					rdSplay.setSelected(false);
					rdHash.setSelected(false);
				}
			}
		});
		rdRedBlack.setBounds(149, 100, 235, 25);
		panel.add(rdRedBlack);
		
		rdSplay = new JRadioButton("SplayTree Implementation");
		rdSplay.setBounds(149, 154, 235, 25);
		rdSplay.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (rdSplay.isSelected()) {
					rdRedBlack.setSelected(false);
					rdHash.setSelected(false);
				}
			}
		});
		panel.add(rdSplay);
		
		rdHash = new JRadioButton("HashMap Implementation");
		rdHash.setBounds(149, 206, 235, 25);
		rdHash.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (rdHash.isSelected()) {
					rdRedBlack.setSelected(false);
					rdSplay.setSelected(false);
				}
			}
		});
		panel.add(rdHash);
		
		JButton btnContinuarF = new JButton("Continuar");
		btnContinuarF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Factory<String,String> fact = new Factory<String,String>();
				if (rdHash.isSelected() || rdRedBlack.isSelected() || rdSplay.isSelected()) {
					if (rdHash.isSelected()) control = new Controlador(fact.getInstance("HashMap"));
					if (rdSplay.isSelected()) control = new Controlador(fact.getInstance("SplayTree"));
					if (rdRedBlack.isSelected()) control = new Controlador(fact.getInstance("RedBlackTree"));
					Inicio.setVisible(true);
					Factory.setVisible(false);
				}else {
					JOptionPane.showMessageDialog(null, "No has seleccionado ninguna opcion");
				}
			}
		});
		btnContinuarF.setBackground(Color.WHITE);
		btnContinuarF.setBounds(195, 319, 139, 34);
		panel.add(btnContinuarF);
		
		Inicio = new JPanel();
		Inicio.setBackground(Color.WHITE);
		frame.getContentPane().add(Inicio, "name_96933071698000");
		Inicio.setLayout(null);
		
		btnNewButton = new JButton("Llenar Diccionario");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				long t =control.fillDic();
				tblTD.setText("Tiempo de creacion de diccionario: " + t + " nanosegundos");
				if (control.isFill()) {
					traduccion.setVisible(true);
				}
				}catch(Exception ex) {
					
				}
			}
		});
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setBounds(12, 13, 147, 25);
		Inicio.add(btnNewButton);
		
		traduccion = new JPanel();
		
		traduccion.setBackground(Color.WHITE);
		traduccion.setBounds(12, 68, 628, 617);
		traduccion.setVisible(false);
		Inicio.add(traduccion);
		traduccion.setLayout(null);
		
		 txtTexto = new JTextArea();
		txtTexto.setBounds(12, 76, 604, 178);
		txtTexto.setLineWrap(true);
		txtTexto.setWrapStyleWord(true);
		txtTexto.setBorder(new LineBorder(new Color(0, 0, 0)));
		traduccion.add(txtTexto);
		
		
		 txtTraduccion = new JTextArea();
		txtTraduccion.setBounds(12, 378, 604, 215);
		txtTraduccion.setLineWrap(true);
		txtTraduccion.setWrapStyleWord(true);
		txtTraduccion.setBorder(new LineBorder(new Color(0, 0, 0)));
		traduccion.add(txtTraduccion);
		
		btnNewButton_1 = new JButton("Texto de txt");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtTexto.setText(control.getTextTxt());
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, "No se logro leer el txt");
					txtTexto.setText("");
				}
			}
		});
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setBounds(494, 38, 122, 25);
		traduccion.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Traducir");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtTexto.getText().toString().isEmpty()) {
					try {
						String[] result = control.traducir(txtTexto.getText().toString());
						txtTraduccion.setText(result[0]);
						lblT.setText("Tiempo de traduccion: " + result[1] + " nanosegundos");
					}catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "No se pudo traducir");
					}
				}
			}
		});
		btnNewButton_2.setBounds(222, 291, 168, 40);
		traduccion.add(btnNewButton_2);
		
		lblT = new JLabel("");
		lblT.setBounds(90, 344, 420, 24);
		traduccion.add(lblT);
		
		tblTD = new JLabel("");
		tblTD.setBounds(197, 13, 420, 24);
		Inicio.add(tblTD);
		
	}
}
