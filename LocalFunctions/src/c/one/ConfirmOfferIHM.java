package c.one;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import c.general.Offer;
import c.general.Request;

public class ConfirmOfferIHM {

	private JFrame frmConfirmOffer;
	private JLabel lblPrice;
	private JLabel lblQuantity;
	private JLabel lblQuality;
	private JLabel lblDelivery;
	private JButton btnOk;
	private JButton btnCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfirmOfferIHM window = new ConfirmOfferIHM();
					window.frmConfirmOffer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConfirmOfferIHM() {
		initialize();
		getOffer();
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
		frmConfirmOffer = new JFrame();
		frmConfirmOffer.setTitle("Confirm Offer of Company B");
		frmConfirmOffer.setBounds(100, 100, 400, 357);
		frmConfirmOffer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmConfirmOffer.getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 22, SpringLayout.NORTH, frmConfirmOffer.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 37, SpringLayout.WEST, frmConfirmOffer.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -53, SpringLayout.SOUTH, frmConfirmOffer.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 345, SpringLayout.WEST, frmConfirmOffer.getContentPane());
		frmConfirmOffer.getContentPane().add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel lblNewLabel = new JLabel("Price");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel, 51, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel, 54, SpringLayout.WEST, panel);
		panel.add(lblNewLabel);
		
		lblPrice = new JLabel("The price");
		sl_panel.putConstraint(SpringLayout.NORTH, lblPrice, 0, SpringLayout.NORTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.WEST, lblPrice, 93, SpringLayout.EAST, lblNewLabel);
		panel.add(lblPrice);
		
		JLabel lblNewLabel_1 = new JLabel("Quantity");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 39, SpringLayout.SOUTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel_1, 0, SpringLayout.WEST, lblNewLabel);
		panel.add(lblNewLabel_1);
		
		lblQuantity = new JLabel("the quantity");
		sl_panel.putConstraint(SpringLayout.NORTH, lblQuantity, 0, SpringLayout.NORTH, lblNewLabel_1);
		sl_panel.putConstraint(SpringLayout.WEST, lblQuantity, 0, SpringLayout.WEST, lblPrice);
		panel.add(lblQuantity);
		
		JLabel lblNewLabel_2 = new JLabel("Quality");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel_2, 33, SpringLayout.SOUTH, lblNewLabel_1);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel_2, 0, SpringLayout.WEST, lblNewLabel);
		panel.add(lblNewLabel_2);
		
		lblQuality = new JLabel("The quality");
		sl_panel.putConstraint(SpringLayout.NORTH, lblQuality, 0, SpringLayout.NORTH, lblNewLabel_2);
		sl_panel.putConstraint(SpringLayout.WEST, lblQuality, 0, SpringLayout.WEST, lblPrice);
		panel.add(lblQuality);
		
		JLabel lblNewLabel_3 = new JLabel("Delivery Time");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel_3, 33, SpringLayout.SOUTH, lblNewLabel_2);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel_3, 0, SpringLayout.WEST, lblNewLabel);
		panel.add(lblNewLabel_3);
		
		lblDelivery = new JLabel("The delivery time");
		sl_panel.putConstraint(SpringLayout.NORTH, lblDelivery, 0, SpringLayout.NORTH, lblNewLabel_3);
		sl_panel.putConstraint(SpringLayout.WEST, lblDelivery, 0, SpringLayout.WEST, lblPrice);
		panel.add(lblDelivery);
		
		btnCancel = new JButton("Cancel");
		springLayout.putConstraint(SpringLayout.NORTH, btnCancel, 6, SpringLayout.SOUTH, panel);
		frmConfirmOffer.getContentPane().add(btnCancel);
		
		btnOk = new JButton("Ok");
		springLayout.putConstraint(SpringLayout.WEST, btnCancel, 6, SpringLayout.EAST, btnOk);
		springLayout.putConstraint(SpringLayout.EAST, btnOk, -87, SpringLayout.EAST, frmConfirmOffer.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 6, SpringLayout.SOUTH, panel);
		frmConfirmOffer.getContentPane().add(btnOk);
	}
	
	public void getOffer(){
		 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			path=path.replaceAll("%20", " ");
			path+="/../myBin/offerFile.req";
			Offer offer= (Offer)c.general.Util.readFromFile(path);
			if(offer!=null){
				lblPrice.setText(offer.getPrice()+"");
				lblQuality.setText(offer.getQuality());
				lblQuantity.setText(offer.getQuantity()+"");
				lblDelivery.setText(offer.getDeliveryTime()+"");
			}
	}
	
	public void returnConfirmationInFile(Boolean confirm){
		
		try {
			 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				path=path.replaceAll("%20", " ");
				path+="/../myBin/confirmFile.req";
	            FileOutputStream fileOut = new FileOutputStream(path);
	            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
	            objectOut.writeObject(confirm);
	            objectOut.close();
	            System.out.println("The Object  was succesfully written to a file");
	 
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	}
	
	public void addEvents(){
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				returnConfirmationInFile(true);
				System.exit(0);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				returnConfirmationInFile(false);
				System.exit(0);
			}
		});
	}
}
