package pt.uab.hm.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pt.uab.hm.Vehicle;

public class ORTRVisualizer {
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;
	
	public static void visualize(List<Vehicle> vehicles) {
		JFrame testFrame = new JFrame();
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final LinesComponent comp = new LinesComponent();
		comp.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		testFrame.getContentPane().add(comp, BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel();
		
		JButton clearButton = new JButton("Clear");
		
		buttonsPanel.add(clearButton);
		testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comp.clearLines();
			}
		});
		
		testFrame.pack();
		testFrame.setVisible(true);
		
		
		for (Vehicle vehicle : vehicles) {
			int factor = 1;
			Color randomColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
			
			for (int i = 0; i < vehicle.getCustomers().size() - 1; i++) {
				double x1 = vehicle.getCustomers().get(i).getPoint().getX();
				double y1 = vehicle.getCustomers().get(i).getPoint().getY();
				double x2 = vehicle.getCustomers().get(i + 1).getPoint().getX();
				double y2 = vehicle.getCustomers().get(i + 1).getPoint().getY();
				comp.addLine((int)x1/factor + WINDOW_WIDTH / 2, (int)y1/factor + WINDOW_HEIGHT / 2, (int)x2/factor + WINDOW_WIDTH / 2, (int)y2/factor + WINDOW_HEIGHT / 2, randomColor);
			}
		}
	}
}
