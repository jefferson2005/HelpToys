package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;

public class Relatorios extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private final JPanel contentPanel = new JPanel();
	private JButton btnFornecedores;
	private JButton btnVenda;
	private JButton btnPatri;

	public static void main(String[] args) {
		try {
			Relatorios dialog = new Relatorios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Relatorios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/hospital2.png")));
		setTitle("Relatórios");
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JButton btnClientes = new JButton("");
		btnClientes.setToolTipText("Relatório de Cliente");
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/relatorioCli.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});

		btnPatri = new JButton("");
		btnPatri.setIcon(new ImageIcon(Relatorios.class.getResource("/img2/Money.png")));
		btnPatri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustoPatrimonio();
			}
		});

		btnVenda = new JButton("");
		btnVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VendaPatrimonio();
			}
		});
		btnVenda.setIcon(new ImageIcon(Relatorios.class.getResource("/img2/Carrinho.png")));
		btnVenda.setBounds(494, 502, 48, 48);
		contentPanel.add(btnVenda);

		JLabel lblNewLabel_1 = new JLabel("Venda Patrimônio:");
		lblNewLabel_1.setBounds(383, 520, 110, 14);
		contentPanel.add(lblNewLabel_1);
		btnPatri.setBounds(272, 502, 48, 48);
		contentPanel.add(btnPatri);

		JLabel lbl = new JLabel("Custo Patrimônio:");
		lbl.setBounds(152, 520, 110, 14);
		contentPanel.add(lbl);
		btnClientes.setBounds(105, 80, 128, 128);
		contentPanel.add(btnClientes);

		JButton btnServicos = new JButton("");
		btnServicos.setToolTipText("Relatório de serviço");
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setIcon(new ImageIcon(Relatorios.class.getResource("/img/Relatorio11.png")));
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioServicos();
			}
		});
		btnServicos.setBounds(494, 80, 128, 128);
		contentPanel.add(btnServicos);

		JButton btnRepor = new JButton("");
		btnRepor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRepor.setIcon(new ImageIcon(Relatorios.class.getResource("/img2/Repor.png")));
		btnRepor.setToolTipText("Reposição ");
		btnRepor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Repor();
			}
		});
		btnRepor.setBounds(403, 255, 128, 128);
		contentPanel.add(btnRepor);

		btnFornecedores = new JButton("");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores();
			}
		});
		btnFornecedores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedores.setIcon(new ImageIcon(Relatorios.class.getResource("/img2/Fornecedor.png")));
		btnFornecedores.setToolTipText("Fornecedores");
		btnFornecedores.setBounds(172, 255, 128, 128);
		contentPanel.add(btnFornecedores);

		JLabel lblNewLabel_3 = new JLabel("\r\n");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(255, 128, 255));
		lblNewLabel_3.setBounds(-1, 485, 784, 75);
		contentPanel.add(lblNewLabel_3);
	}

	private void relatorioClientes() {

		Document document = new Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));

			document.open();

			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));

			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" "));

			String readClientes = "select nome,fone,email from clientes order by nome";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(readClientes);

				rs = pst.executeQuery();

				PdfPTable tabela = new PdfPTable(3);

				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);

				while (rs.next()) {

					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}

				document.add(tabela);

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		document.close();

		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioServicos() {

		Document document = new Document();

		document.setPageSize(PageSize.A4.rotate());

		try {

			PdfWriter.getInstance(document, new FileOutputStream("servicos.pdf"));

			document.open();

			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Servicos:"));
			document.add(new Paragraph(""));

			String readServicos = "select os,dataOS,brinquedo,defeito,valor,nome from servicos inner join clientes\r\n"
					+ "on servicos.id = clientes.idcli;";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(readServicos);

				rs = pst.executeQuery();

				PdfPTable tabela = new PdfPTable(6);

				PdfPCell col1 = new PdfPCell(new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Data"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Brinquedo"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Defeito"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Valor"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Nome"));

				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);

				while (rs.next()) {

					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}

				document.add(tabela);

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		document.close();

		try {
			Desktop.getDesktop().open(new File("produtos.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void Repor() {

		Document document = new Document();

		document.setPageSize(PageSize.A4.rotate());

		try {

			PdfWriter.getInstance(document, new FileOutputStream("produtos.pdf"));

			document.open();

			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));

			document.add(new Paragraph("Produtos:"));
			document.add(new Paragraph(""));

			String readProdutos = "select codigo as código,produto,date_format(dataval, '%d/%m/%Y') as validade,\n"
					+ "date_format(dataent, '%d/%m/%Y') as entrada,\n" + "estoque, estoquemin as estoque_mínimo \n"
					+ "from produtos where dataval < dataent or estoque<estoquemin ";
			try {

				con = dao.conectar();
				pst = con.prepareStatement(readProdutos);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(6);
				PdfPCell col1 = new PdfPCell(new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Produto"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Validade"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Entrada"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Estoque"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Estoque min"));

				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);

				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		document.close();

		try {
			Desktop.getDesktop().open(new File("produtos.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void CustoPatrimonio() {

		Document document = new Document();

		document.setPageSize(PageSize.A4.rotate());

		try {

			PdfWriter.getInstance(document, new FileOutputStream("patrimonio.pdf"));

			document.open();

			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));

			document.add(new Paragraph("Patrímônio:"));
			document.add(new Paragraph(""));

			String readPatrimonio = "select sum(custo * estoque) as Total from produtos;";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(readPatrimonio);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(1);

				PdfPCell col1 = new PdfPCell(new Paragraph("Patrímônio"));

				tabela.addCell(col1);

				while (rs.next()) {

					tabela.addCell(rs.getString(1));

				}

				document.add(tabela);

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		document.close();

		try {
			Desktop.getDesktop().open(new File("patrimonio.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void VendaPatrimonio() {

		Document document = new Document();

		document.setPageSize(PageSize.A4.rotate());

		try {

			PdfWriter.getInstance(document, new FileOutputStream("patrimonio.pdf"));

			document.open();

			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));

			document.add(new Paragraph("Patrímônio:"));
			document.add(new Paragraph(""));

			String readPatrimonio = "select sum((custo +(custo * lucro)/100) * estoque) as total from produtos;";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(readPatrimonio);

				rs = pst.executeQuery();

				PdfPTable tabela = new PdfPTable(1);

				PdfPCell col1 = new PdfPCell(new Paragraph("Patrímônio"));

				tabela.addCell(col1);

				while (rs.next()) {

					tabela.addCell(rs.getString(1));

				}

				document.add(tabela);

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		document.close();

		try {
			Desktop.getDesktop().open(new File("patrimonio.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void Fornecedores() {

		Document document = new Document();

		document.setPageSize(PageSize.A4.rotate());

		try {

			PdfWriter.getInstance(document, new FileOutputStream("fornecedores.pdf"));

			document.open();

			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));

			document.add(new Paragraph("Fornecedores:"));
			document.add(new Paragraph(""));
			String readPatrimonio = " select \n" + " idfornecedores,\n" + " razao,\n" + " fantasia,\n" + " fone,\n"
					+ " email,\n" + " site,\n" + " cnpj,\n" + " ie,\n" + " cep" + " from fornecedor;";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(readPatrimonio);

				rs = pst.executeQuery();

				PdfPTable tabela = new PdfPTable(9);

				PdfPCell col1 = new PdfPCell(new Paragraph("ID Fornecedor"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Razão Social"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Nome Fantasia"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Número de Telefone"));
				PdfPCell col5 = new PdfPCell(new Paragraph("E-mail"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Site"));
				PdfPCell col7 = new PdfPCell(new Paragraph("CPNJ"));
				PdfPCell col8 = new PdfPCell(new Paragraph("Inscrição Estadual"));
				PdfPCell col9 = new PdfPCell(new Paragraph("CEP"));

				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				tabela.addCell(col7);
				tabela.addCell(col8);
				tabela.addCell(col9);

				while (rs.next()) {

					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(7));
					tabela.addCell(rs.getString(8));
					tabela.addCell(rs.getString(9));

				}

				document.add(tabela);

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		document.close();
		try {
			Desktop.getDesktop().open(new File("fornecedores.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
