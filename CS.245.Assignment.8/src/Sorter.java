import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Sorter extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;
	
	public static final String SELECTION_SORT_TEXT = "Selection Sort";
	public static final String INSERTION_SORT_TEXT = "Insertion Sort";
	public static final String MERGE_SORT_TEXT = "Merge Sort";
	private JButton sortBtn;
	private JLabel loadingImage;
	private JComboBox algorithmSelection;
	
	public Sorter() {
		setTitle("Sorter");
	    setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    createContents();
	    setVisible(true);
	    setResizable(false);
	}
	
	private void createContents() {
		SortButtonListener listener = new SortButtonListener();
		String[] algorithms = { "Selection Sort", "Insertion Sort", "Merge Sort" };
		
		//create JPanel, and JTextArea display
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8));
		
		// Create center components
		JTextArea centerText = new JTextArea();
		centerText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerText.setEditable(false);
		
		
		// Create elements for northPanel
		JLabel sorting = new JLabel("Sorting algorithm:");
		algorithmSelection  = new JComboBox(algorithms);
		algorithmSelection.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
		sortBtn = new JButton("Sort");
		sortBtn.addActionListener(listener);
		ImageIcon loadingIcon = new ImageIcon(this.getClass().getResource("loading.gif"));
		loadingImage = new JLabel(loadingIcon);
		// Setting preferredSize breaks off half this image (WHYYYYY)
		//loadingImage.setPreferredSize(new Dimension(25,25));
		loadingImage.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
		loadingImage.setVisible(false);
		
		// Add elements to northPanel
		northPanel.add(sorting);
		northPanel.add(algorithmSelection);
		northPanel.add(sortBtn);
		northPanel.add(loadingImage);
		
		
		// Add panels to JFrame
		add(centerText, BorderLayout.CENTER);
		add(northPanel, BorderLayout.NORTH);
		
		
	}
	
	private class SortButtonListener implements ActionListener {
		private int[] arr;
		private SorterRunnable sr;
		
		public void actionPerformed(ActionEvent e) {
			ExecutorService es = Executors.newSingleThreadExecutor();
			//TODO: Finish Implementation
			
			
			if(e.getSource() == sortBtn) {
				loadingImage.setVisible(true);
				algorithmSelection.setEnabled(false);
				sortBtn.setEnabled(false);
				
				if(algorithmSelection.getSelectedIndex() == 0) {
					System.out.println("Selection Sort");
				}
				
			}
			
			es.shutdown();
	    }
		
		private void fillArr() {
			Random r = new Random();
			for(int i=0; i<arr.length; ++i) {
				arr[i] = r.nextInt();
			}
		}
	}
	
	public synchronized void displayResult(int n, long runtime) {
		//TODO: Implement
	}
	
	public static void main(String[] args) {
		new Sorter();
	}
}