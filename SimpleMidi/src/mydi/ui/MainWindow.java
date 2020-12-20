package mydi.ui;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;

import javax.sound.midi.MidiDevice;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import mydi.MidiKeyboardPlayer;
import mydi.MidiKeyboardPlayer.InstrumentItem;

public class MainWindow {

	MidiKeyboardPlayer kbd ;
	
	private JFrame frmSimpleMidi;
	private JComboBox<String> comboBox;
	private JComboBox<InstrumentItem> comboBox_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmSimpleMidi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSimpleMidi = new JFrame();
		frmSimpleMidi.setResizable(false);
		frmSimpleMidi.setTitle("Simple Midi");
		frmSimpleMidi.setBounds(100, 100, 450, 300);
		frmSimpleMidi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Keyboard");
		
		comboBox = new JComboBox<String>();
		
		JLabel lblNewLabel_1 = new JLabel("Instrument");
		
		comboBox_1 = new JComboBox();
		GroupLayout groupLayout = new GroupLayout(frmSimpleMidi.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox, 0, 351, Short.MAX_VALUE)
						.addComponent(comboBox_1, 0, 351, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(205, Short.MAX_VALUE))
		);
		frmSimpleMidi.getContentPane().setLayout(groupLayout);
		
		//----------------- end Generated ------------------------
		
		
		
		kbd = new MidiKeyboardPlayer();
		
		for(MidiDevice.Info info : kbd.deviceInfos) {
			getKeyBoardComboBox().addItem(info.getName());
		}
		
		getKeyBoardComboBox().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				
				openKBD((String) e.getItem());
				
			}
			
		});
		
		getInstrumentComboBox().addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				setInstru((InstrumentItem) arg0.getItem());
				
			}
		});
		
	}
	public JComboBox<String> getKeyBoardComboBox() {
		return comboBox;
	}
	public JComboBox<InstrumentItem> getInstrumentComboBox() {
		return comboBox_1;
	}
	
	private void openKBD(String s) {
		kbd.openKeaboard(s);
		
		for(InstrumentItem itm : kbd.instrus) {
			getInstrumentComboBox().addItem(itm);
		}
		
		
	}
	
	private void setInstru(InstrumentItem instr) {
		
		kbd.setInstru(instr.id);
		
	}
}
