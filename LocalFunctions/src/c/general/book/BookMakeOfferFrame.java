package c.general.book;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import c.one.ConfirmOfferIHM;
import c.one.Util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookMakeOfferFrame {

	private JFrame frmMakeOfferTo;
	private JTextField textFieldQt1;
	private JTextField textFieldPc1;
	private JTextField textFieldQt2;
	private JTextField textFieldPc2;
	private JTextField textFieldQt3;
	private JTextField textFieldPc3;
	private JButton btnOk;
	private JButton btnCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookMakeOfferFrame window = new BookMakeOfferFrame();
					window.frmMakeOfferTo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookMakeOfferFrame() {
		initialize();
		addEvents();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
			// UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel");
			UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			Logger.getLogger(ConfirmOfferIHM.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		frmMakeOfferTo = new JFrame();
		frmMakeOfferTo.setTitle("Offer to Bookstore A");
		frmMakeOfferTo.setBounds(100, 100, 449, 473);
		frmMakeOfferTo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmMakeOfferTo.getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		springLayout.putConstraint(SpringLayout.NORTH, panel, 33, SpringLayout.NORTH, frmMakeOfferTo.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 67, SpringLayout.WEST, frmMakeOfferTo.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 360, SpringLayout.WEST, frmMakeOfferTo.getContentPane());
		frmMakeOfferTo.getContentPane().add(panel);
		
		btnCancel = new JButton("Cancel");
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -19, SpringLayout.NORTH, btnCancel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel lblNewLabel = new JLabel("Quantity");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel, 61, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel, 26, SpringLayout.WEST, panel);
		panel.add(lblNewLabel);
		
		textFieldQt1 = new JTextField();
		textFieldQt1.setText("300");
		sl_panel.putConstraint(SpringLayout.NORTH, textFieldQt1, -3, SpringLayout.NORTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.WEST, textFieldQt1, 32, SpringLayout.EAST, lblNewLabel);
		panel.add(textFieldQt1);
		textFieldQt1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Book 1");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 27, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, lblNewLabel_1, -54, SpringLayout.EAST, panel);
		panel.add(lblNewLabel_1);
		
		textFieldPc1 = new JTextField();
		textFieldPc1.setText("200");
		sl_panel.putConstraint(SpringLayout.NORTH, textFieldPc1, 17, SpringLayout.SOUTH, textFieldQt1);
		sl_panel.putConstraint(SpringLayout.EAST, textFieldPc1, 0, SpringLayout.EAST, textFieldQt1);
		panel.add(textFieldPc1);
		textFieldPc1.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price");
		sl_panel.putConstraint(SpringLayout.NORTH, lblPrice, 3, SpringLayout.NORTH, textFieldPc1);
		sl_panel.putConstraint(SpringLayout.WEST, lblPrice, 0, SpringLayout.WEST, lblNewLabel);
		panel.add(lblPrice);
		
		JLabel lblBook = new JLabel("Book 2");
		sl_panel.putConstraint(SpringLayout.NORTH, lblBook, 99, SpringLayout.SOUTH, lblNewLabel_1);
		sl_panel.putConstraint(SpringLayout.WEST, lblBook, 0, SpringLayout.WEST, lblNewLabel_1);
		panel.add(lblBook);
		
		JLabel lblQuantity = new JLabel("Quantity");
		sl_panel.putConstraint(SpringLayout.NORTH, lblQuantity, 65, SpringLayout.SOUTH, lblPrice);
		sl_panel.putConstraint(SpringLayout.WEST, lblQuantity, 0, SpringLayout.WEST, lblNewLabel);
		panel.add(lblQuantity);
		
		textFieldQt2 = new JTextField();
		textFieldQt2.setText("400");
		sl_panel.putConstraint(SpringLayout.NORTH, textFieldQt2, 0, SpringLayout.NORTH, lblQuantity);
		sl_panel.putConstraint(SpringLayout.WEST, textFieldQt2, 0, SpringLayout.WEST, textFieldQt1);
		panel.add(textFieldQt2);
		textFieldQt2.setColumns(10);
		
		JLabel lblPrice_1 = new JLabel("Price");
		sl_panel.putConstraint(SpringLayout.NORTH, lblPrice_1, 26, SpringLayout.SOUTH, lblQuantity);
		sl_panel.putConstraint(SpringLayout.WEST, lblPrice_1, 0, SpringLayout.WEST, lblNewLabel);
		panel.add(lblPrice_1);
		
		textFieldPc2 = new JTextField();
		textFieldPc2.setText("200");
		sl_panel.putConstraint(SpringLayout.WEST, textFieldPc2, 0, SpringLayout.WEST, textFieldQt1);
		sl_panel.putConstraint(SpringLayout.SOUTH, textFieldPc2, 0, SpringLayout.SOUTH, lblPrice_1);
		panel.add(textFieldPc2);
		textFieldPc2.setColumns(10);
		
		JLabel lblBook_1 = new JLabel("Book 3");
		sl_panel.putConstraint(SpringLayout.WEST, lblBook_1, 0, SpringLayout.WEST, lblNewLabel_1);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblBook_1, -87, SpringLayout.SOUTH, panel);
		panel.add(lblBook_1);
		
		JLabel lblQuantity_1 = new JLabel("Quantity");
		sl_panel.putConstraint(SpringLayout.NORTH, lblQuantity_1, 44, SpringLayout.SOUTH, lblPrice_1);
		sl_panel.putConstraint(SpringLayout.WEST, lblQuantity_1, 0, SpringLayout.WEST, lblNewLabel);
		panel.add(lblQuantity_1);
		
		textFieldQt3 = new JTextField();
		textFieldQt3.setText("300");
		sl_panel.putConstraint(SpringLayout.NORTH, textFieldQt3, 0, SpringLayout.NORTH, lblQuantity_1);
		sl_panel.putConstraint(SpringLayout.EAST, textFieldQt3, 0, SpringLayout.EAST, textFieldQt1);
		panel.add(textFieldQt3);
		textFieldQt3.setColumns(10);
		
		JLabel lblPrice_2 = new JLabel("Price");
		sl_panel.putConstraint(SpringLayout.WEST, lblPrice_2, 0, SpringLayout.WEST, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblPrice_2, -10, SpringLayout.SOUTH, panel);
		panel.add(lblPrice_2);
		
		textFieldPc3 = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, textFieldPc3, -3, SpringLayout.NORTH, lblPrice_2);
		sl_panel.putConstraint(SpringLayout.WEST, textFieldPc3, 0, SpringLayout.WEST, textFieldQt1);
		textFieldPc3.setText("100");
		panel.add(textFieldPc3);
		textFieldPc3.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Add / Remove Book");
		panel.add(btnNewButton_2);
		springLayout.putConstraint(SpringLayout.SOUTH, btnCancel, -10, SpringLayout.SOUTH, frmMakeOfferTo.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnCancel, -10, SpringLayout.EAST, frmMakeOfferTo.getContentPane());
		frmMakeOfferTo.getContentPane().add(btnCancel);
		
		btnOk = new JButton("Ok");
		springLayout.putConstraint(SpringLayout.SOUTH, btnOk, 0, SpringLayout.SOUTH, btnCancel);
		springLayout.putConstraint(SpringLayout.EAST, btnOk, -6, SpringLayout.WEST, btnCancel);
		frmMakeOfferTo.getContentPane().add(btnOk);
	}
	
	
	public Offer makeOffer(){
		Offer off=new Offer();
		off.getBookPrices()[0]=Integer.parseInt(textFieldPc1.getText().trim());
		off.getBookPrices()[1]=Integer.parseInt(textFieldPc2.getText().trim());
		off.getBookPrices()[2]=Integer.parseInt(textFieldPc3.getText().trim());
		
		off.getBookQuantities()[0]=Integer.parseInt(textFieldQt1.getText().trim());
		off.getBookQuantities()[1]=Integer.parseInt(textFieldQt2.getText().trim());
		off.getBookQuantities()[2]=Integer.parseInt(textFieldQt3.getText().trim());
		
		
		
		return off;
	}
	
	public void addEvents(){
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Offer off = makeOffer();
				createOffer(off);
				System.exit(0);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Offer off = makeOffer();
				System.exit(0);
			}
		});
		
		
		
	}
	
	public void createOffer(Offer off){ 
	try {
		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			path=path.replaceAll("%20", " ");
			path+="/../myBin/offerFile.req";
           FileOutputStream fileOut = new FileOutputStream(path);
           ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
           objectOut.writeObject(off);
           objectOut.close();
           System.out.println("The Object  was succesfully written to a file");

       } catch (Exception ex) {
           ex.printStackTrace();
       }
	}
}
