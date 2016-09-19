package LEDProyecto;//##MUST CHANGE THIS FOR YOUR PACKAGE NAME##

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.awt.event.ActionEvent;

public class config extends JFrame {

	private JPanel contentPane;
	private static config frame;

	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new config();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public static ArrayList<String> port = new<String> ArrayList();
	public static JComboBox comboBox = new JComboBox();
	private OutputStream output=null;
	SerialPort serialPort;
	private String PORT="";
	private final int TIMEOUT=2000;
	private final int DATARATE=9600;
	private boolean First = true;
	private JList list;
	private JLabel lblSelect;
	private JButton btnColor;

	public config() {
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

		setTitle("Config");
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
					CommPortIdentifier actualPuertoID=(CommPortIdentifier) puertoEnum.nextElement();
					if(PORT.equals(actualPuertoID.getName())){
						puertoID=actualPuertoID;
						break;
					}
				}
				if (puertoID==null){
					SendError("Can't conect to serial port");
					System.exit(ERROR);
					return;
				}
				try {
					serialPort = (SerialPort) puertoID.open(this.getClass().getName(),TIMEOUT);
					serialPort.setSerialPortParams(DATARATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					output = serialPort.getOutputStream();
				} catch (Exception i) {
					SendError(i.getMessage()+"If the Arduino consol is open close it and try again");
					System.exit(ERROR);
				}
				setBounds(100, 100, 300, 435);
				comboBox.setEnabled(false);
				lblSelect.setText("Port select:");
				setTitle("Port: "+PORT);
			}
		});

		comboBox.setBounds(6, 21, 288, 27);
		contentPane.add(comboBox);

		lblSelect = new JLabel("Select port and Start ");
		lblSelect.setBounds(6, 6, 174, 16);
		contentPane.add(lblSelect);
		
		btnColor = new JButton("Color");
		btnColor.setBounds(0, 135, 40, 40);
		contentPane.add(btnColor);
	}
	private void SendError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje,"ERROR",JOptionPane.ERROR_MESSAGE);
	}
}
