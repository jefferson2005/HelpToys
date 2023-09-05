package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.net.URI;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Sobre() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Sobre.class.getResource("/img/993813_matreshka_matrioshka_mother_open_souvenir_icon.png")));
		getContentPane().setBackground(new Color(255, 255, 255));
		setModal(true);
		setTitle("Sobre");
		setBounds(100, 100, 680, 300);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("sistema OS");
		lblNewLabel.setBounds(10, 68, 215, 14);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Autor: Jefferson L");
		lblNewLabel_1.setBounds(10, 236, 152, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Sob a licença MIT:");
		lblNewLabel_2.setBounds(520, 122, 122, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("MIT");
		lblNewLabel_3.setBounds(503, 122, 128, 128);
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel(
				"Sistema para gestão de serviços e controle de estoqueHospital de Brinquedos");
		lblNewLabel_4.setBounds(10, 11, 632, 35);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Web service:");
		lblNewLabel_5.setBounds(10, 134, 82, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblGit = new JLabel("https://github.com/jefferson2005/HelpToys");
		lblGit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblGit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://github.com/jefferson2005/HelpToys");
			}
		});
		lblGit.setForeground(new Color(0, 0, 255));
		lblGit.setBounds(91, 134, 247, 14);
		getContentPane().add(lblGit);

	}

	private void link(String site) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(site);
			desktop.browse(uri);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
