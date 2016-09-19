package LEDProyecto;//### THE PACKAGEW NAME MUST CHAGE TO YOUR'S PACKAGE NAME ###

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import manejarchoosecolor.Marco;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.nio.channels.SelectableChannel;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LED_Arduino extends JFrame {

	private JPanel contentPane;
	private static LED_Arduino frame;
	private int i=0;

	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LED_Arduino();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private ArrayList <JRadioButton> selcc;
	private ArrayList <JButton> LEDs;
	private  ArrayList <JButton> colors;
	public static ArrayList<String> port = new<String> ArrayList();
	public static JComboBox comboBox = new JComboBox();
	private OutputStream output=null;
	SerialPort serialPort;
	private String PORT="/dev/cu.wchusbserial1410";
	private final int TIMEOUT=2000;
	private final int DATARATE=9600;
	private boolean First = true;
	private JList list;
	private JLabel lblSelect;
	private JButton btnColor;
	private int cLEDs;
	private JLabel lblbri;
	private JSlider slider;

	public LED_Arduino() {
		if(First){
			CommPortIdentifier serialPortId;
			// static CommPortIdentifier sSerialPortId;
			Enumeration enumComm;
			// SerialPort serialPort;

			enumComm = CommPortIdentifier.getPortIdentifiers();
			while (enumComm.hasMoreElements()) {
				serialPortId = (CommPortIdentifier) enumComm.nextElement();
				if (serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					comboBox.addItem(serialPortId.getName());
					port.add(serialPortId.getName());

				}
			}
			First=false;
		}

		setTitle("LED_Arduino");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 72);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		list = new JList();
		list.setBounds(27, 53, 181, -20);
		contentPane.add(list);
		comboBox.setEditable(false);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PORT=port.get(comboBox.getSelectedIndex());
				CommPortIdentifier puertoID=null;
				Enumeration puertoEnum=CommPortIdentifier.getPortIdentifiers();
				while(puertoEnum.hasMoreElements()){
					CommPortIdentifier actualPortID=(CommPortIdentifier) puertoEnum.nextElement();
					if(PORT.equals(actualPortID.getName())){
						puertoID=actualPortID;
						break;
					}
				}
				if (puertoID==null){
					SendError("Can't connect to serial port");
					System.exit(ERROR);
					return;
				}
				try {
					serialPort = (SerialPort) puertoID.open(this.getClass().getName(),TIMEOUT);
					serialPort.setSerialPortParams(DATARATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					output = serialPort.getOutputStream();
				} catch (Exception i) {
					SendError(i.getMessage()+"Close the arduino console if open");
					System.exit(ERROR);
				}
				setBounds(100, 100, 300, 435);
				comboBox.setEnabled(false);
				lblSelect.setText("Port select:");
				setTitle("Puerto: "+PORT);
			}
		});

		comboBox.setBounds(6, 21, 288, 27);
		contentPane.add(comboBox);

		lblSelect = new JLabel("Select port and Start ");
		lblSelect.setBounds(6, 6, 174, 16);
		contentPane.add(lblSelect);
		
		JButton secAll = new JButton("Selct all LED");
		secAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			for (int a = 0; a < 7; a++) {
				if(selcc.get(a).isSelected()){
					for (int j = 0; j < 32; j++) {
						enviarLED(colors.get(a).getForeground(), j+1);
						LEDs.get(j).setForeground(colors.get(a).getForeground());
						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					break;
				}
			}
			}
		});
		secAll.setForeground(Color.black);
		secAll.setBounds(210, 125, 90, 40);
		
		contentPane.add(secAll);
		
		
		JButton sec1a8 = new JButton("Selct 1-8");
		sec1a8.setForeground(Color.black);
		sec1a8.setBounds(210, 165, 90, 40);
		sec1a8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			for (int a = 0; a < 7; a++) {
				if(selcc.get(a).isSelected()){
					for (int j = 0; j < 8; j++) {
						enviarLED(colors.get(a).getForeground(), j);
						LEDs.get(j).setForeground(colors.get(a).getForeground());
					}
					break;
				}
			}
			}
		});
		contentPane.add(sec1a8);
		
		JButton sec9a16 = new JButton("Selct 9-16");
		sec9a16.setForeground(Color.black);
		sec9a16.setBounds(210, 205, 90, 40);
		sec9a16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			for (int a = 0; a < 7; a++) {
				if(selcc.get(a).isSelected()){
					for (int j = 8; j < 16; j++) {
						enviarLED(colors.get(a).getForeground(), j);
						LEDs.get(j).setForeground(colors.get(a).getForeground());
					}
					break;
				}
			}
			}
		});
		contentPane.add(sec9a16);
		
		
		JButton sec17a24 = new JButton("Selct 17-24");
		sec17a24.setForeground(Color.black);
		sec17a24.setBounds(210, 245, 90, 40);
		sec17a24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			for (int a = 0; a < 7; a++) {
				if(selcc.get(a).isSelected()){
					for (int j = 16; j < 24; j++) {
						enviarLED(colors.get(a).getForeground(), j);
						LEDs.get(j).setForeground(colors.get(a).getForeground());
					}
					break;
				}
			}
			}
		});
		contentPane.add(sec17a24);
		
		
		JButton EXIT = new JButton("EXIT");
		EXIT.setForeground(Color.black);
		EXIT.setBounds(210, 325, 90, 40);
		EXIT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			System.exit(0);
			}
		});
		contentPane.add(EXIT);
		
		slider = new JSlider();
		slider.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblbri.setText("Brigthes: "+slider.getValue());
				
			}
		});
		
		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblbri.setText("Brigthes: "+slider.getValue());
			}
		});
		
		slider.setBounds(3, 375, 292, 41);
		slider.setMaximum(255);
		slider.setMinimum(0);
		
		contentPane.add(slider);
		
		lblbri = new JLabel("Brigthes: "+slider.getValue());
		lblbri.setBounds(6, 365, 100, 16);
		contentPane.add(lblbri);
		
		JButton btnRainbow = new JButton("RainBow");
		btnRainbow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enviarLED(2);
			}
		});
		btnRainbow.setBounds(210, 285, 90, 40);
		contentPane.add(btnRainbow);
		
	     colors= new <Button> ArrayList();
	     selcc=new <JRadioButton> ArrayList();
	     ButtonGroup colorsel = new ButtonGroup();
	        for (i=0; i<7; i++){
	        	colors.add(new JButton(""));
	        	colors.get(i).setFont(new Font("Dialog",Font.PLAIN,28));
	        	colors.get(i).setForeground(Color.black);
	        	colors.get(i).setBounds(5+i*42, 60, 40, 40);
	        	colors.get(i).setName(String.valueOf(i));
	        	selcc.add(new JRadioButton(""));
	        	selcc.get(i).setBounds(1+i*42, 78, 23, 23);
	        	colorsel.add(selcc.get(i));
	        	
	        	colors.get(i).addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	            	 JButton botonPulsado= (JButton)evt.getSource();  
	                 //System.out.println(botonPulsado.getName());
	                 Color c;
	                 c = JColorChooser.showDialog(
	                           ((Component)evt.getSource()).getParent(),
	                           "Colors.LED", Color.blue);
	                 botonPulsado.setForeground(c);
	            }
	        });
	        	contentPane.add(selcc.get(i));
	            contentPane.add(colors.get(i));
	        }
	        LEDs = new <Button> ArrayList();
	        cLEDs=0;
		        for (i=0; i<3; i++){
		        	for(int j=0;j<8;j++){
		        	LEDs.add(new JButton("·"));
		        	LEDs.get(cLEDs).setFont(new Font("Dialog",Font.PLAIN,48));
		        	LEDs.get(cLEDs).setForeground(Color.black);
		        	LEDs.get(cLEDs).setBounds(6+i*42,125+j*30, 40, 30);
		        	LEDs.get(cLEDs).setBackground(Color.WHITE);
		        	LEDs.get(cLEDs).setName(String.valueOf(i));
		        	LEDs.get(cLEDs).setName(""+cLEDs);
		        	LEDs.get(cLEDs).addMouseListener(new java.awt.event.MouseAdapter() {
		            public void mouseClicked(java.awt.event.MouseEvent evt) {
		            	 JButton botonPulsado= (JButton)evt.getSource();  
		                 //System.out.println(botonPulsado.getName());
		            	 for (int a = 0; a < 7; a++) {
		     				if(selcc.get(a).isSelected()){
		     					int j=Integer.parseInt(botonPulsado.getName());
		     						enviarLED(colors.get(a).getForeground(), j);
		     						LEDs.get(j).setForeground(colors.get(a).getForeground());			
		     					break;
		     				}
		     			}
		                
		            }
		        });
		            contentPane.add(LEDs.get(cLEDs));
		            cLEDs++;
		        	}
		        }
		        
	}
	private void SendError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje,"ERROR",JOptionPane.ERROR_MESSAGE);
	}
	private void enviarLED(Color c, int led){
		try {
			
			output.write(c.getRed());
			output.write(c.getGreen());
			output.write(c.getBlue());
			output.write(led);
			output.write(slider.getValue());
			output.write(1);
		
		
			//Thread.sleep(100);
		
		} catch (Exception e) {
			// TODO: handle exception
			SendError("ERROR");
			System.exit(ERROR);
		}
	}
	private void enviarLED(int mode){
		try {
			
			output.write(0);
			output.write(0);
			output.write(0);
			output.write(0);
			output.write(slider.getValue());
			output.write(mode);
		
		
			//Thread.sleep(100);
		
		} catch (Exception e) {
			// TODO: handle exception
			SendError("ERROR");
			System.exit(ERROR);
		}
	}
}
