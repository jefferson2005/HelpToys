package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import model.DAO;
import java.awt.Color;
import java.awt.Font;

public class Principal extends JFrame {
	
	//Instanciar objetos JDBC
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	private JButton btnProdutos;
	//essa label será alterada pela tela de login
	public JLabel lblUsuario;
	public JButton btnRelatorio;
	public JButton bntUsuarios;
	public JLabel lblRodape;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	/**
	 * Create the frame.
	 */
	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}

			private void status() {
				try {
					//abrir a conexão 
					con = dao.conectar();
					if (con == null) {
						//System.out.println("Erro de conexão");
						lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/dboff.png")));
					} else {
						//System.out.println("Banco conectado");
						lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/2124501_app_check_data_essential_ui_icon.png")));
					}
					// NUNCA esquecer de fechar a conexão 
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}//Fim do método status()
			
				/**
				 * Método responsável por setar a data do rodapé 
				 */
				private void setarData() {
					Date date = new Date();
					//Criar objeto para formatar a data
					DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
					//alterar o texto da label pela data atual formatada
					lblData.setText(formatador.format(date));
		
				}	
				
		});
		setTitle("Sistema - Hospital de brinquedos");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/993813_matreshka_matrioshka_mother_open_souvenir_icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblStatus = new JLabel("");
		lblStatus.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
			{
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Usuário:");
		lblNewLabel_2.setBounds(4, 499, 86, 14);
		contentPane.add(lblNewLabel_2);
		
		lblUsuario = new JLabel("");
		lblUsuario.setBounds(56, 499, 224, 14);
		contentPane.add(lblUsuario);
		
		lblData = new JLabel("New label");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblData.setBounds(4, 524, 437, 26);
		contentPane.add(lblData);
		lblStatus.setBounds(710, 480, 74, 91);
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/2124505_app_data_essential_ui_icon.png")));
		lblStatus.setToolTipText("DBon");
		contentPane.add(lblStatus);
		
		bntUsuarios = new JButton("");
		bntUsuarios.setEnabled(false);
		bntUsuarios.setContentAreaFilled(false);
		bntUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//abrir a tela de Usuaríos
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		bntUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bntUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/usuarios (2).png")));
		bntUsuarios.setToolTipText("Usuários");
		bntUsuarios.setBounds(554, 127, 64, 64);
		contentPane.add(bntUsuarios);
		
		JButton bntSobre = new JButton("");
		bntSobre.setContentAreaFilled(false);
		bntSobre.setBorder(null);
		bntSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bntSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);

			}
		});
		bntSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		bntSobre.setToolTipText("Sobre");
		bntSobre.setBounds(726, 11, 48, 48);
		contentPane.add(bntSobre);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/Hospital.png")));
		lblNewLabel.setBounds(308, 11, 133, 122);
		contentPane.add(lblNewLabel);
		
		lblRodape = new JLabel("");
		lblRodape.setOpaque(true);
		lblRodape.setBackground(new Color(255, 128, 255));
		lblRodape.setBounds(0, 480, 784, 81);
		contentPane.add(lblRodape);
		
		JLabel lblNomeEmpresa = new JLabel("Toys Help");
		lblNomeEmpresa.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblNomeEmpresa.setBounds(330, 140, 92, 26);
		contentPane.add(lblNomeEmpresa);
		
		JButton btnClientes = new JButton("");
		btnClientes.setContentAreaFilled(false);
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/User.png")));
		btnClientes.setBounds(112, 127, 64, 64);
		contentPane.add(btnClientes);
		
		JButton btnOrdemSRV = new JButton("");
		btnOrdemSRV.setContentAreaFilled(false);
		btnOrdemSRV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servico = new Servicos();
				servico.setVisible(true);
			}
		});
		btnOrdemSRV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOrdemSRV.setIcon(new ImageIcon(Principal.class.getResource("/img/OrdemSRV.png")));
		btnOrdemSRV.setToolTipText("Ordem de Serviço");
		btnOrdemSRV.setBounds(186, 233, 64, 64);
		contentPane.add(btnOrdemSRV);
		
		btnRelatorio = new JButton("");
		btnRelatorio.setEnabled(false);
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setContentAreaFilled(false);
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);	
			}
		});
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/Relatorio (2).png")));
		btnRelatorio.setToolTipText("Relátorio");
		btnRelatorio.setBounds(481, 233, 64, 64);
		contentPane.add(btnRelatorio);
		
		btnProdutos = new JButton("");
		btnProdutos.setToolTipText("Produtos");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
			}
		});
		btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/Produtos.png")));
		btnProdutos.setContentAreaFilled(false);
		btnProdutos.setBounds(112, 332, 64, 64);
		contentPane.add(btnProdutos);
		
		JButton btnFornecedor = new JButton("");
		btnFornecedor.setToolTipText("Fornecedor");
		btnFornecedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedor.setIcon(new ImageIcon(Principal.class.getResource("/img/Fornecedor.png")));
		btnFornecedor.setContentAreaFilled(false);
		btnFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setVisible(true);
			}
		});
		btnFornecedor.setBounds(564, 332, 64, 64);
		contentPane.add(btnFornecedor);
	}
}

