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
import javax.swing.JTextArea;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class Servicos extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

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
	private JTextArea txtDescricao;
	private JLabel lblNewLabel_1;
	private JTextField txtAtendente;

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

	public Servicos() {
		getContentPane().setBackground(new Color(255, 255, 255));
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
		lblOS.setBounds(98, 26, 46, 14);
		getContentPane().add(lblOS);

		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblData.setBounds(98, 91, 46, 14);
		getContentPane().add(lblData);

		JLabel lblBrinquedo = new JLabel("Brinquedo:");
		lblBrinquedo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBrinquedo.setBounds(94, 213, 80, 26);
		getContentPane().add(lblBrinquedo);

		JLabel lblDefeito = new JLabel("Defeito:");
		lblDefeito.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDefeito.setBounds(99, 267, 80, 14);
		getContentPane().add(lblDefeito);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValor.setBounds(98, 351, 46, 14);
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
		txtOS.setBounds(129, 25, 86, 26);
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
		txtData.setBounds(139, 88, 132, 26);
		getContentPane().add(txtData);
		txtData.setColumns(10);

		txtBrinquedo = new JTextField();
		txtBrinquedo.setBounds(180, 218, 370, 26);
		getContentPane().add(txtBrinquedo);
		txtBrinquedo.setColumns(10);

		txtDefeito = new JTextField();
		txtDefeito.setBounds(180, 263, 370, 26);
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
		btnBuscar.setBounds(226, 23, 48, 48);
		getContentPane().add(btnBuscar);

		btnLimpar = new JButton("");
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
		panel.setBounds(447, 28, 230, 93);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPaneCliente = new JScrollPane();
		scrollPaneCliente.setVisible(false);
		scrollPaneCliente.setBounds(9, 43, 210, 32);
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
		txtValor.setBounds(140, 347, 86, 26);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescricao.setBounds(236, 312, 100, 17);
		getContentPane().add(lblDescricao);

		txtDescricao = new JTextArea();
		txtDescricao.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtDescricao.setBounds(317, 313, 344, 117);
		getContentPane().add(txtDescricao);

		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(255, 128, 255));
		lblNewLabel_1.setBounds(696, 0, 88, 561);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setOpaque(true);
		lblNewLabel_1_1.setBackground(new Color(255, 128, 255));
		lblNewLabel_1_1.setBounds(0, 0, 88, 561);
		getContentPane().add(lblNewLabel_1_1);

		JLabel lblAtendente = new JLabel("Atendente:");
		lblAtendente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAtendente.setBounds(98, 172, 82, 14);
		getContentPane().add(lblAtendente);

		txtAtendente = new JTextField();
		txtAtendente.setBounds(181, 169, 158, 26);
		getContentPane().add(txtAtendente);
		txtAtendente.setColumns(10);

	}

	private void limparCampos() {
		txtData.setText(null);
		txtDefeito.setText(null);
		txtBrinquedo.setText(null);
		txtID.setText(null);
		txtOS.setText(null);
		txtValor.setText(null);
		btnBuscar.setEnabled(true);
		txtCliente.setText(null);
		txtDescricao.setText(null);
		txtAtendente.setText(null);
	}

	private void buscar() {

		String numOS = JOptionPane.showInputDialog("Número da OS");

		String read = "select * from servicos where os = ?";

		try {

			con = dao.conectar();

			pst = con.prepareStatement(read);

			pst.setString(1, numOS);

			rs = pst.executeQuery();

			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtBrinquedo.setText(rs.getString(3));
				txtDefeito.setText(rs.getString(4));
				txtValor.setText(rs.getString(5));
				txtDescricao.setText(rs.getString(6));
				txtAtendente.setText(rs.getString(7));
				txtID.setText(rs.getString(8));

				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			} else {

				JOptionPane.showMessageDialog(null, "serviço inexistente");
				btnAdicionar.setEnabled(true);

			}

		} catch (Exception e) {
			System.out.print(e);
		}
	}

	private void adicionar() {

		if (txtBrinquedo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Brinquedo do Cliente");
			txtBrinquedo.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito do Brinquedo do Cliente");
			txtDefeito.requestFocus();
		} else {

			String create = "insert into servicos (brinquedo,defeito,valor,descricao,id) value (?, ?, ?, ?, ?)";

			try {
				con = dao.conectar();

				pst = con.prepareStatement(create);
				pst.setString(1, txtBrinquedo.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtDescricao.getText());
				pst.setString(5, txtAtendente.getText());
				pst.setString(6, txtID.getText());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Ordem de serviço adicionado");
				limparCampos();

			} catch (Exception e) {
				System.out.print(e);
			}
		}
	}

	private void excluirServico() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste serviço ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {

			String delete = "delete from servicos where OS=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(delete);

				pst.setString(1, txtOS.getText());

				pst.executeUpdate();

				limparCampos();

				JOptionPane.showMessageDialog(null, " Serviço excluido");

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarServico() {

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

			String update = "update servicos set os=?, brinquedo=?, defeito=?, valor=?, descricao=? where id=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(update);
				pst.setString(1, txtOS.getText());
				pst.setString(2, txtBrinquedo.getText());
				pst.setString(3, txtDefeito.getText());
				pst.setString(4, txtValor.getText());
				pst.setString(5, txtDescricao.getText());
				pst.setString(6, txtAtendente.getText());
				pst.setString(7, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do serviço editado com sucesso");

				limparCampos();

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarCliente() {

		DefaultListModel<String> modelo = new DefaultListModel<>();

		listCliente.setModel(modelo);

		String readLista = "select* from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
		try {

			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			while (rs.next()) {

				scrollPaneCliente.setVisible(true);

				modelo.addElement(rs.getString(2));

				if (txtCliente.getText().isEmpty()) {
					scrollPaneCliente.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarCliente() {

		int linha = listCliente.getSelectedIndex();
		if (linha >= 0) {

			String readListaUsuario = "select * from clientes where nome like '" + txtCliente.getText() + "%'"
					+ "order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {

					scrollPaneCliente.setVisible(false);

					txtID.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		} else {

			scrollPaneCliente.setVisible(false);
		}
	}

	private void imprimirOS() {

		Document document = new Document();

		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do Cliente");
			txtID.requestFocus();
		} else if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione a OS");
			txtOS.requestFocus();
		} else {
			try {

				PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));

				document.open();
				String readOS = "Select * from servicos where os = ?";

				try {

					con = dao.conectar();

					pst = con.prepareStatement(readOS);
					pst.setString(1, txtOS.getText());

					rs = pst.executeQuery();

					if (rs.next()) {

						Paragraph os = new Paragraph("OS: " + rs.getString(1));
						os.setAlignment(Element.ALIGN_RIGHT);
						document.add(os);

						Paragraph data = new Paragraph("Data: " + rs.getString(2));
						data.setAlignment(Element.ALIGN_LEFT);
						document.add(data);

						Paragraph usuario = new Paragraph("Brinquedo: " + rs.getString(3));
						usuario.setAlignment(Element.ALIGN_LEFT);
						document.add(usuario);

						Paragraph defeito = new Paragraph("Defeito: " + rs.getString(4));
						defeito.setAlignment(Element.ALIGN_LEFT);
						document.add(defeito);

						Paragraph valor = new Paragraph("Valor: " + rs.getString(5));
						valor.setAlignment(Element.ALIGN_LEFT);
						document.add(valor);

						Paragraph descricao = new Paragraph("Descrição: " + rs.getString(6));
						descricao.setAlignment(Element.ALIGN_LEFT);
						document.add(descricao);

						Image imagem = Image.getInstance(Servicos.class.getResource("/img/Hospital.png"));
						imagem.scaleToFit(192, 148);
						imagem.setAbsolutePosition(20, 300);
						document.add(imagem);
					}

					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} catch (Exception e) {
				System.out.println(e);
			}

			document.close();

			try {
				Desktop.getDesktop().open(new File("os.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
