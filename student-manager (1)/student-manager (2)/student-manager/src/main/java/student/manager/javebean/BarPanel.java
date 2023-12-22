package student.manager.javebean;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BarPanel extends JPanel {
    int HEIGHT = 250;
    int offsetY = 14;
	int TOTAL;
    Font font = new Font("Times", Font.PLAIN, 14);

    private Map<String, Integer> map;

    public BarPanel(Map<String, Integer> map, Integer total) {
        this.map = map;
        this.TOTAL = total;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        HEIGHT = (int) (getHeight());
        int width = getWidth();
        int w80 = (int) (width * 0.8);
        int w20 = (int) (width * 0.2);
        int uw = w80 / map.size();
        int gap = w20 / (map.size() + 1);

        int bottom = getHeight() - 20;
        int rectH = 0;
        String str;
        g.setFont(font);
		int i = 0;
        for (Map.Entry<String, Integer> stringIntegerEntry : map.entrySet()) {
			i++;
			str = stringIntegerEntry.getKey() + " - " + stringIntegerEntry.getValue();
			rectH = (int)(HEIGHT * stringIntegerEntry.getValue() / TOTAL);
			g.setColor(getRandomColor());
			g.fillRect(gap * i + uw * (i-1), bottom - rectH, uw, rectH);
			g.setColor(Color.BLACK);
			g.drawString(str, gap * i + uw * (i-1), bottom - rectH - offsetY);
        }


        int x1, x2, y1, y2;
        x1 = gap / 2;
        y1 = bottom;
        x2 = width - gap / 2;
        y2 = y1;
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);

    }

    private Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);

        return new Color(r, g, b);
    }
}