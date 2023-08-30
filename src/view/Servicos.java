package view;

import java.awt.EventQueue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.Font;

public class Servicos extends JDialog {
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtBrinquedo;
	private JTextField txtDefeito;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JButton btnBuscar;
	private JButton btnLimpar;
	private JTextField txtCliente;
	private JTextField txtID;
	private JList listCliente;
	private JScrollPane scrollPaneCliente;
	private JLabel lblNewLabel;
	private JTextField txtValor;
	private JButton btnOS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/hospital2.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneCliente.setVisible(false);
			}
		});
		setTitle("Serviços ");
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		
		btnOS = new JButton("");
		btnOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/impressora.png")));
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(343, 502, 48, 48);
		getContentPane().add(btnOS);
		
		JLabel lblOS = new JLabel("OS:");
		lblOS.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOS.setBounds(88, 80, 46, 14);
		getContentPane().add(lblOS);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(88, 145, 46, 14);
		getContentPane().add(lblData);
		
		JLabel lblBrinquedo = new JLabel("Brinquedo:");
		lblBrinquedo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBrinquedo.setBounds(89, 233, 80, 26);
		getContentPane().add(lblBrinquedo);
		
		JLabel lblDefeito = new JLabel("Defeito:");
		lblDefeito.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDefeito.setBounds(89, 285, 80, 14);
		getContentPane().add(lblDefeito);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValor.setBounds(90, 342, 46, 14);
		getContentPane().add(lblValor);
		
		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtOS.setBounds(119, 79, 86, 26);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);
		
		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtData.setBounds(129, 142, 132, 26);
		getContentPane().add(txtData);
		txtData.setColumns(10);
		
		txtBrinquedo = new JTextField();
		txtBrinquedo.setBounds(175, 238, 370, 26);
		getContentPane().add(txtBrinquedo);
		txtBrinquedo.setColumns(10);
		
		txtDefeito = new JTextField();
		txtDefeito.setBounds(175, 282, 370, 26);
		getContentPane().add(txtDefeito);
		txtDefeito.setColumns(10);
		txtDefeito.setDocument(new Validador(200));
		
		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/ADD.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBounds(145, 502, 48, 48);
		getContentPane().add(btnAdicionar);
		
		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/Editor.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarServico();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(244, 502, 48, 48);
		getContentPane().add(btnEditar);
		
		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/Excluir Contato.png")));
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirServico();
			}
		});
		btnExcluir.setBounds(447, 502, 48, 48);
		getContentPane().add(btnExcluir);
		
		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/Achar.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(216, 77, 48, 48);
		getContentPane().add(btnBuscar);
		
		btnLimpar =new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/clear icon.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(540, 502, 48, 48);
		getContentPane().add(btnLimpar);
		
		getRootPane().setDefaultButton(btnBuscar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(377, 82, 230, 93);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPaneCliente = new JScrollPane();
		scrollPaneCliente.setVisible(false);
		scrollPaneCliente.setBounds(9, 47, 210, 32);
		panel.add(scrollPaneCliente);
		
		listCliente = new JList();
		listCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarCliente();
			}
		});
		scrollPaneCliente.setViewportView(listCliente);
		
		txtCliente = new JTextField();
		txtCliente.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarCliente();
			}
		});
		txtCliente.setBounds(10, 21, 210, 26);
		panel.add(txtCliente);
		txtCliente.setColumns(10);
		
		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtID.setEditable(false);
		txtID.setBounds(34, 52, 86, 20);
		panel.add(txtID);
		txtID.setColumns(10);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(10, 55, 46, 14);
		panel.add(lblID);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBackground(new Color(255, 128, 255));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(0, 486, 784, 75);
		getContentPane().add(lblNewLabel);
		
		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtValor.setBounds(132, 339, 86, 26);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

	}// construtor
	
	
	/**
	 * METODO LIMPAR CAMPOS
	 */
	private void limparCampos() {
		txtData.setText(null);
		txtDefeito.setText(null);
		txtBrinquedo.setText(null);
		txtID.setText(null);
		txtOS.setText(null);
		txtValor.setText(null);
		btnBuscar.setEnabled(true);
		txtCliente.setText(null);		
	}
	
	/**
	 * METODO BUSCAR
	 */
	private void buscar() {
		
		//captura do número da OS (sem usar a caixa de texto)
		String numOS = JOptionPane.showInputDialog("Número da OS");
		
		
		String read = "select * from servicos where os = ?";
		// Tratamento de exceções
		try {
			// Abrir a conexão
			con = dao.conectar();

			// Preparar a exucução da query(instrução sql - CRUD Read)
			// O paraêmtro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);
			//substituir a ? (parâmetro) pelo número da OS
			pst.setString(1, numOS);
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else parar verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtBrinquedo.setText(rs.getString(3)); 
				txtDefeito.setText(rs.getString(4));
				txtValor.setText(rs.getString(5));
				txtID.setText(rs.getString(6));
				
				
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "serviço inexistente");
				btnAdicionar.setEnabled(true);
				
			}

		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatóriios
		if  (txtBrinquedo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Brinquedo do Cliente");
			txtBrinquedo.requestFocus();
		}else if(txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito do Brinquedo do Cliente");
			txtDefeito.requestFocus();
		} else {
			
			// lógica pricipal
			// CRUD Creat
			String create = "insert into servicos (brinquedo,defeito,valor,id) value (?, ?, ?, ?)";
			// tratamento com exceções
			try {
				//abrir conexão 
				con = dao.conectar();
				//preparar a execução da query(instrução sql - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1, txtBrinquedo.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtID.getText());
				
				//executar a query(instruição sql (CRUD - Creat))
				pst.executeUpdate();
				//Confirmar
				JOptionPane.showMessageDialog(null, "Ordem de serviço adicionado");  
				limparCampos();
				//fechar a conexão
			} catch (Exception e) {
				System.out.print(e);
			}
			}
	}
	private void excluirServico() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confima captura a opção escolhida

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste serviço ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			// CRUD - Delete
			String delete = "delete from servicos where OS=?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				// substituir a ? pelo id do contato
				pst.setString(1, txtOS.getText());
				// executar a query
				pst.executeUpdate();
				// limpar Campos
				limparCampos();
				// exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, " Serviço excluido");
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	private void editarServico() {
		// System.out.println("teste do Método");

		// Validação dos campos obrigátorios
		if (txtBrinquedo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Brinquedo do Serviço");
			txtBrinquedo.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Valor de Serviço");
			txtValor.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Defeito do Serviço");
			txtDefeito.requestFocus();
		} else {

			// Lógica principal
			// CRUD - Update
			String update = "update servicos set os=?, brinquedo=?, defeito=?, valor=? where id=?";
			// tratamentos de exceçoes
			try {
				// como a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtOS.getText());
				pst.setString(2, txtBrinquedo.getText());
				pst.setString(3, txtDefeito.getText());
				pst.setString(4, txtValor.getText());
				pst.setString(5, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do serviço editado com sucesso");
				// limpar campos
				limparCampos();
				// fechar conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	private void listarCliente() {
		// System.out.println("Teste");
		// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// obejto irá temporariamente armazenar os dados
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o model (vetor na lista)
		listCliente.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select* from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
		try {
			// abri conexão
			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			// uso do while para trazer os usuários enquanto exisitr
			while (rs.next()) {
				// mostrar a lista
				scrollPaneCliente.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelo.addElement(rs.getString(2));
				// esconder a lista se nenhuma letra for digitada
				if (txtCliente.getText().isEmpty()) {
					scrollPaneCliente.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Método usado para buscar usuário pela lista
	 */
	private void buscarCliente() {
		// System.out.println("teste");
		// variável que captura o indice da linha da lista
		int linha = listCliente.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução sql)
			// limit (0,1) -> seleciona o indice 0 e 1 usuário da lista
			String readListaUsuario =  "select * from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPaneCliente.setVisible(false);
					// setar campos
					txtID.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
			scrollPaneCliente.setVisible(false);
		}
	}
	
	/**
	 * Impressão da OS
	 */
	private void imprimirOS() {
		// instanciar objeto para usar os métodos da biblioteca
		Document document = new Document();
		// documento pdf
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do Cliente");
			txtID.requestFocus();
		} else if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione a OS");
			txtOS.requestFocus();
		}
		else {
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			String readOS = "Select * from servicos where os = ?";
			// conexão com o banco
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql)
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				// executar a query
				rs = pst.executeQuery();
				// se existir a OS
				if (rs.next()) {					
					//document.add(new Paragraph("OS: " + rs.getString(1)));
					Paragraph os = new Paragraph ("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);
						
					Paragraph usuario = new Paragraph ("Brinquedo: " + rs.getString(3));
					usuario.setAlignment(Element.ALIGN_LEFT);
					document.add(usuario);
					
					Paragraph defeito = new Paragraph ("Defeito: " + rs.getString(4));
					defeito.setAlignment(Element.ALIGN_LEFT);
					document.add(defeito);
					
					Paragraph data = new Paragraph ("Data: " + rs.getString(2));
					data.setAlignment(Element.ALIGN_LEFT);
					document.add(data);
					
					Paragraph valor = new Paragraph ("Valor: " + rs.getString(6));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
				
				
				
					//imprimir imagens
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/Hospital.png"));
					imagem.scaleToFit(192,148);
					imagem.setAbsolutePosition(20, 300);
					document.add(imagem);					
				}
				// fechar a conexão com o banco
				con.close();
				} catch (Exception e) {
					System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	}
}
