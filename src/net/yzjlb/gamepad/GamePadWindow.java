package net.yzjlb.gamepad;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Robot;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.yzjlb.gamepad.GamePadServer.MessageListener;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JScrollPane;
import org.json.JSONObject;

import javafx.scene.input.KeyCode;

public class GamePadWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	GamePadServer server;
	private JScrollPane scrollPane;
	private Robot robot;
	HashMap<String, Integer> map_key;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GamePadWindow frame = new GamePadWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GamePadWindow() {
		map_key = new HashMap<String, Integer>();
		map_key.put("up", KeyEvent.VK_UP);
		map_key.put("down", KeyEvent.VK_DOWN);
		map_key.put("left", KeyEvent.VK_LEFT);
		map_key.put("right", KeyEvent.VK_RIGHT);
		map_key.put("a", KeyEvent.VK_A);
		map_key.put("b", KeyEvent.VK_B);
		map_key.put("c", KeyEvent.VK_C);
		map_key.put("d", KeyEvent.VK_D);
		map_key.put("e", KeyEvent.VK_E);
		map_key.put("f", KeyEvent.VK_F);
		map_key.put("g", KeyEvent.VK_G);
		map_key.put("h", KeyEvent.VK_H);
		map_key.put("i", KeyEvent.VK_I);
		map_key.put("j", KeyEvent.VK_J);
		map_key.put("k", KeyEvent.VK_K);
		map_key.put("l", KeyEvent.VK_L);
		map_key.put("m", KeyEvent.VK_M);
		map_key.put("n", KeyEvent.VK_N);
		map_key.put("o", KeyEvent.VK_O);
		map_key.put("p", KeyEvent.VK_P);
		map_key.put("q", KeyEvent.VK_Q);
		map_key.put("r", KeyEvent.VK_R);
		map_key.put("s", KeyEvent.VK_S);
		map_key.put("t", KeyEvent.VK_T);
		map_key.put("u", KeyEvent.VK_U);
		map_key.put("v", KeyEvent.VK_V);
		map_key.put("w", KeyEvent.VK_W);
		map_key.put("x", KeyEvent.VK_X);
		map_key.put("y", KeyEvent.VK_Y);
		map_key.put("z", KeyEvent.VK_Z);
		map_key.put("shift", KeyEvent.VK_SHIFT);
		map_key.put("enter", KeyEvent.VK_ENTER);
		map_key.put("0", KeyEvent.VK_0);
		map_key.put("1", KeyEvent.VK_1);
		map_key.put("2", KeyEvent.VK_2);
		map_key.put("3", KeyEvent.VK_3);
		map_key.put("4", KeyEvent.VK_4);
		map_key.put("5", KeyEvent.VK_5);
		map_key.put("6", KeyEvent.VK_6);
		map_key.put("7", KeyEvent.VK_7);
		map_key.put("8", KeyEvent.VK_8);
		map_key.put("9", KeyEvent.VK_9);
		map_key.put("space", KeyEvent.VK_SPACE);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		contentPane.add(horizontalBox_1);
		
		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_2);
		
		textField = new JTextField();
		horizontalBox_2.add(textField);
		textField.setColumns(1);
		textField.setText("2020");
		textField.setMaximumSize(new Dimension(600,25));
		
		
		JButton btnNewButton = new JButton("启动server");
		horizontalBox_2.add(btnNewButton);
		final JTextPane textPane = new JTextPane();
		scrollPane = new JScrollPane(textPane);
		verticalBox.add(scrollPane);
		setTitle("WebSocket服务端测试 - 风的影子");
//		窗口置顶
		setAlwaysOnTop(true);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(server!=null && server.isRun()){
					server.stop();
				}
				server = new GamePadServer(Integer.valueOf(textField.getText()) );
				server.start();
				server.setMessageListener(new MessageListener() {
					
					@Override
					public void onMessage(String msg) {
						String text = textPane.getText();
						text+= msg+"\n";
						textPane.setText(text);
						if(msg.startsWith("{")){
							JSONObject obj = new JSONObject(msg);
							String action = obj.getString("action");
							if(action.equals("keydown")){
								String value = obj.getString("value");
								int keycode = 0;
								if(value.equals("up")){
									keycode = KeyEvent.VK_UP;
								}
								else if(value.equals("down")){
									keycode = KeyEvent.VK_DOWN;
								}
								else if(value.equals("left")){
									keycode = KeyEvent.VK_LEFT;
								}
								else if(value.equals("right")){
									keycode = KeyEvent.VK_RIGHT;
								}
								keycode = map_key.get(value);
								robot.keyPress(keycode);
								System.out.println("按下"+keycode);
								
							}
							else if(action.equals("keyup")){
								String value = obj.getString("value");
								int keycode = 0;
								if(value.equals("up")){
									keycode = KeyEvent.VK_UP;
								}
								else if(value.equals("down")){
									keycode = KeyEvent.VK_DOWN;
								}
								else if(value.equals("left")){
									keycode = KeyEvent.VK_LEFT;
								}
								else if(value.equals("right")){
									keycode = KeyEvent.VK_RIGHT;
								}
								keycode = map_key.get(value);
								robot.keyRelease(keycode);
								System.out.println("释放"+keycode);
							}
							else{
								System.out.println("未知类型"+action);
							}
							
							
						}
						else{
							
						}
						
						
					}
				});
			}
		});
		btnNewButton.doClick();
		try {
			robot = new Robot();
			// 延时100毫秒
	        robot.delay(100);
			// 移动鼠标到指定屏幕坐标
//	        robot.mouseMove(100, 100);
//	        robot.keyPress(KeyEvent.VK_A);
//	        robot.keyRelease(KeyEvent.VK_A);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
