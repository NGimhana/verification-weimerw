package raykernel.apps.readability.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import raykernel.apps.readability.code.Function;

public class ReadabilityApplet extends Applet
{

	private static final long serialVersionUID = 1L;

	PortableEvaluator re;

	String readability = "Readability: ";

	private TextArea input;
	private JLabel output;

	Applet thisApplet;

	public void init()
	{
		thisApplet = this;

		re = new PortableEvaluator();

		// Construct the TextFields
		this.input = new TextArea(8, 60);
		this.output = new JLabel("                                                          ");
		//this.output.setEditable(false);
		Button b = new Button("Calculate");

		// add the button to the layout
		this.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		JPanel textPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel outPanel = new JPanel();

		bottomPanel.add(buttonPanel);
		bottomPanel.add(outPanel);

		topPanel.add(new JLabel("Readability Metric"));
		textPanel.add(input);
		buttonPanel.add(b);
		outPanel.add(output);

		this.add(topPanel, BorderLayout.NORTH);
		this.add(textPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.setSize(500, 200);

		// specify that action events sent by the
		// button or the input TextField should be handled 
		// by the same CapitalizerAction object
		ReadabilityAction ca = new ReadabilityAction();
		b.addActionListener(ca);
		//this.input.addComponentListener(ca);

		// notice that ActionEvents produced by output are ignored.

	}

	class ReadabilityAction implements ActionListener
	{

		public void actionPerformed(ActionEvent ae)
		{

			String s = input.getText();

			Function f = new Function(s);

			double read = re.getReadability(f);

			output.setText(readability + read);

		}
	}

}
