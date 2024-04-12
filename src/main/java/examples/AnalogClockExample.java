package examples;

import core.Matrix;
import core.MatrixTransform;
import core.Tuple;
import draw.Canvas;
import draw.Color;
import utils.FileUtils;

public class AnalogClockExample {

    private static final int CANVAS_SIZE = 500;
    private static final int CENTER_X = CANVAS_SIZE / 2;
    private static final int CENTER_Y = CANVAS_SIZE / 2;
    private static final int CLOCK_RADIUS = 200;
    private static final int HOUR_MARKER_LENGTH = 5;

    public static void main(String[] args) {
        Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);

        // Compute rotation matrix for each hour and draw hour markers
        for (int hour = 0; hour < 12; hour++) {
            double angle = Math.toRadians(hour * 30); // 30 degrees per hour
            Matrix rotationMatrix = MatrixTransform.rotationY(angle);
            Tuple hourPosition = rotationMatrix.multiply(new Tuple(0, 0, 1, 1));

            int x = (int) Math.round(CENTER_X + CLOCK_RADIUS * hourPosition.getX());
            int y = (int) Math.round(CENTER_Y + CLOCK_RADIUS * hourPosition.getZ());

            drawHourMarker(canvas, x, y);
        }
        String ppmData = Canvas.canvasToPPM(canvas);
        FileUtils.writeBytesToFile("clock.ppm", ppmData.getBytes());
        System.out.println("draw.Canvas saved to: clock.ppm");
    }

    private static void drawHourMarker(Canvas canvas, int x, int y) {
        int markerLength = HOUR_MARKER_LENGTH;
        int startX = x - markerLength / 2;
        int startY = y - markerLength / 2;
        int endX = x + markerLength / 2;
        int endY = y + markerLength / 2;

        for (int px = startX; px <= endX; px++) {
            for (int py = startY; py <= endY; py++) {
                if (isOnClockFace(px, py)) {
                    canvas.setPixelAt(px, py, Color.WHITE);
                }
            }
        }
    }

    private static boolean isOnClockFace(int x, int y) {
        int distanceSquared = (x - CENTER_X) * (x - CENTER_X) + (y - CENTER_Y) * (y - CENTER_Y);
        return distanceSquared <= CLOCK_RADIUS * CLOCK_RADIUS && distanceSquared >= (CLOCK_RADIUS - HOUR_MARKER_LENGTH) * (CLOCK_RADIUS - HOUR_MARKER_LENGTH);
    }
}