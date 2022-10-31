package c.one;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import c.general.Request;



public class RequestForm {

	private JFrame frmMakeRequest;
	private JTextField maxPriceTextField;
	private JTextField deliveryTimeTextField;
	private JTextField qualityTextField;
	private JTextField QuantityTextField;
	private JButton btnOk;
	private JButton btnCancel;
	private Request request;
	private Boolean monitor=false;
	private static Object lock = new Object();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RequestForm window = new RequestForm();
					window.frmMakeRequest.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RequestForm() {
		initialize();
		addEvents();
	}
	public void setVisible(Boolean bool){
    //   Thread t= new Thread(new Runnable(){

	//	@Override
	//	public void run() {
			// TODO Auto-generated method stub
		
			frmMakeRequest.setVisible(true);
	//	}
    	   
      // });
      // t.start();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
			// UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel");
			UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			Logger.getLogger(RequestForm.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frmMakeRequest = new JFrame();
		frmMakeRequest.setTitle("Make Request");
		frmMakeRequest.setBounds(100, 100, 360, 408);
		frmMakeRequest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmMakeRequest.getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, frmMakeRequest.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 45, SpringLayout.WEST, frmMakeRequest.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -76, SpringLayout.SOUTH, frmMakeRequest.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -36, SpringLayout.EAST, frmMakeRequest.getContentPane());
		frmMakeRequest.getContentPane().add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel lblMaxPriceLabel = new JLabel("Max Price");
		sl_panel.putConstraint(SpringLayout.NORTH, lblMaxPriceLabel, 46, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblMaxPriceLabel, 10, SpringLayout.WEST, panel);
		panel.add(lblMaxPriceLabel);
		
		maxPriceTextField = new JTextField();
		maxPriceTextField.setText("30000");
		sl_panel.putConstraint(SpringLayout.NORTH, maxPriceTextField, -12, SpringLayout.NORTH, lblMaxPriceLabel);
		sl_panel.putConstraint(SpringLayout.WEST, maxPriceTextField, 58, SpringLayout.EAST, lblMaxPriceLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, maxPriceTextField, 72, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, maxPriceTextField, -39, SpringLayout.EAST, panel);
		panel.add(maxPriceTextField);
		maxPriceTextField.setColumns(10);
		
		JLabel lblMaxDeliveryTime = new JLabel("Max Delivery Time");
		sl_panel.putConstraint(SpringLayout.NORTH, lblMaxDeliveryTime, 46, SpringLayout.SOUTH, lblMaxPriceLabel);
		sl_panel.putConstraint(SpringLayout.WEST, lblMaxDeliveryTime, 0, SpringLayout.WEST, lblMaxPriceLabel);
		panel.add(lblMaxDeliveryTime);
		
		deliveryTimeTextField = new JTextField();
		deliveryTimeTextField.setText("5");
		sl_panel.putConstraint(SpringLayout.NORTH, deliveryTimeTextField, 28, SpringLayout.SOUTH, maxPriceTextField);
		sl_panel.putConstraint(SpringLayout.WEST, deliveryTimeTextField, 0, SpringLayout.WEST, maxPriceTextField);
		sl_panel.putConstraint(SpringLayout.SOUTH, deliveryTimeTextField, 66, SpringLayout.SOUTH, maxPriceTextField);
		sl_panel.putConstraint(SpringLayout.EAST, deliveryTimeTextField, 0, SpringLayout.EAST, maxPriceTextField);
		panel.add(deliveryTimeTextField);
		deliveryTimeTextField.setColumns(10);
		
		JLabel lblQualityLabel = new JLabel("Quality");
		sl_panel.putConstraint(SpringLayout.NORTH, lblQualityLabel, 57, SpringLayout.SOUTH, lblMaxDeliveryTime);
		sl_panel.putConstraint(SpringLayout.WEST, lblQualityLabel, 0, SpringLayout.WEST, lblMaxPriceLabel);
		panel.add(lblQualityLabel);
		
		qualityTextField = new JTextField();
		qualityTextField.setText("A");
		sl_panel.putConstraint(SpringLayout.SOUTH, qualityTextField, -80, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, qualityTextField, 27, SpringLayout.SOUTH, deliveryTimeTextField);
		sl_panel.putConstraint(SpringLayout.WEST, qualityTextField, 0, SpringLayout.WEST, maxPriceTextField);
		sl_panel.putConstraint(SpringLayout.EAST, qualityTextField, 0, SpringLayout.EAST, maxPriceTextField);
		panel.add(qualityTextField);
		qualityTextField.setColumns(10);
		
		JLabel lblQuantityLabel = new JLabel("Quantity");
		sl_panel.putConstraint(SpringLayout.NORTH, lblQuantityLabel, 44, SpringLayout.SOUTH, lblQualityLabel);
		sl_panel.putConstraint(SpringLayout.WEST, lblQuantityLabel, 0, SpringLayout.WEST, lblMaxPriceLabel);
		panel.add(lblQuantityLabel);
		
		QuantityTextField = new JTextField();
		QuantityTextField.setText("5000");
		sl_panel.putConstraint(SpringLayout.NORTH, QuantityTextField, 0, SpringLayout.NORTH, lblQuantityLabel);
		sl_panel.putConstraint(SpringLayout.WEST, QuantityTextField, 0, SpringLayout.WEST, maxPriceTextField);
		sl_panel.putConstraint(SpringLayout.SOUTH, QuantityTextField, 38, SpringLayout.NORTH, lblQuantityLabel);
		sl_panel.putConstraint(SpringLayout.EAST, QuantityTextField, -39, SpringLayout.EAST, panel);
		panel.add(QuantityTextField);
		QuantityTextField.setColumns(10);
		
		btnCancel = new JButton("Cancel");
		springLayout.putConstraint(SpringLayout.SOUTH, btnCancel, 0, SpringLayout.SOUTH, frmMakeRequest.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnCancel, 0, SpringLayout.EAST, frmMakeRequest.getContentPane());
		frmMakeRequest.getContentPane().add(btnCancel);
		
		btnOk = new JButton("Ok");
		springLayout.putConstraint(SpringLayout.NORTH, btnOk, 0, SpringLayout.NORTH, btnCancel);
		springLayout.putConstraint(SpringLayout.EAST, btnOk, -11, SpringLayout.WEST, btnCancel);
		frmMakeRequest.getContentPane().add(btnOk);
		//frmMakeRequest.setVisible(true);
		
	}
	
	private void addEvents(){
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				request= new Request();
				request.setMaxDeliveryTime(Integer.parseInt(deliveryTimeTextField.getText().trim()));
				request.setMaxPrice(Integer.parseInt(maxPriceTextField.getText().trim()));
				request.setQuality(qualityTextField.getText().trim());
				request.setQuantity(Integer.parseInt(QuantityTextField.getText().trim()));
				createFile(request);
				System.exit(0);
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				request= new Request();
				createFile(request);
				System.exit(0);
			}
		});
	}

	public Request getARequest() {
	
		return request;
	}

	public Request getRequest(){
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	


	public static Object getLock() {
		return lock;
	}

	public static void setLock(Object lock) {
		RequestForm.lock = lock;
	}
	
	public void createFile(Request req){
		 try {
			 String path=Util.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				path=path.replaceAll("%20", " ");
				path+="\\requestFile.req";
	            FileOutputStream fileOut = new FileOutputStream(path);
	            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
	            objectOut.writeObject(req);
	            objectOut.close();
	            System.out.println("The Object  was succesfully written to a file");
	 
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	}
	
	
}
