package examples;

import core.geometry.Matrix;
import core.geometry.MatrixTransform;
import core.geometry.Tuple;
import draw.Canvas;
import draw.Color;
import utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AnalogClockExample {

    private static final int CANVAS_SIZE = 500;
    private static final int CENTER_X = CANVAS_SIZE / 2;
    private static final int CENTER_Y = CANVAS_SIZE / 2;
    private static final int CLOCK_RADIUS = 200;
    private static final int HOUR_MARKER_LENGTH = 20;

    public static void main(String[] args) {
        Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        drawGrid(canvas);
        drawClock(canvas);
        String ppmData = Canvas.canvasToPPM(canvas);

        try {
            // Delete the file if it exists
            Files.deleteIfExists(Path.of("clock.ppm"));
            System.out.println("File deleted successfully (if it existed).");
        } catch (IOException e) {
            System.err.println("Error deleting the file: " + e.getMessage());
        }
        FileUtils.writeBytesToFile("clock.ppm", ppmData.getBytes());
        System.out.println("draw.Canvas saved to: clock.ppm");
    }

    private static void drawGrid(Canvas canvas) {
        for (int x = 0; x < canvas.getWidth(); x+=10) {
            if (x % 50 == 0) {
                canvas.setPixelAt(x, canvas.getHeight() / 2, Color.GREEN);
            } else {
                canvas.setPixelAt(x, canvas.getHeight() / 2, Color.WHITE);
            }
        }
        for (int y = 0; y < canvas.getHeight(); y+=10) {
            if (y % 50 == 0) {
                canvas.setPixelAt(canvas.getWidth() / 2, y, Color.RED);
            } else {
                canvas.setPixelAt(canvas.getWidth() / 2, y, Color.WHITE);
            }
        }
    }

    private static void drawClock(Canvas canvas) {
        // Compute rotation matrix for each hour and draw hour markers
        for (int hour = 0; hour < 12; hour++) {
            double angle = Math.toRadians(hour * 30); // 30 degrees per hour
            Matrix rotationMatrix = MatrixTransform.rotationY(angle);
            Tuple hourPosition = rotationMatrix.multiply(new Tuple(0, 0, 1, 1));

            int x = (int) Math.round(CENTER_X + CLOCK_RADIUS * hourPosition.getX());
            int y = (int) Math.round(CENTER_Y + CLOCK_RADIUS * hourPosition.getZ());

            System.out.println("hour = " + hour);
            System.out.println("hour x position = " + x);
            System.out.println("hour y position = " + y);

            drawHourMarker(canvas, x, y);
        }
    }

    private static void drawHourMarker(Canvas canvas, int x, int y) {
        int markerLength = HOUR_MARKER_LENGTH;
        HourPosition hour = getHourPosition(x, y, markerLength);

        for (int px = hour.startX(); px <= hour.endX(); px++) {
            for (int py = hour.startY(); py <= hour.endY(); py++) {
                if (isOnClockFace(px, py)) {
                    canvas.setPixelAt(px, py, Color.WHITE);
                }
            }
        }
    }

    private static HourPosition getHourPosition(int x, int y, int markerLength) {
        int quarterX = x < CENTER_X ? -1 : 1; // Determine if the hour is on the left or right side
        int quarterY = y < CENTER_Y ? -1 : 1; // Determine if the hour is on the top or bottom side

        int startX, startY, endX, endY;

        if (quarterX == -1 && quarterY == -1) { // Top left quarter
            startX = x - markerLength ;
            startY = y - markerLength;
            endX = x;
            endY = y;
        } else if (quarterX == 1 && quarterY == -1) { // Top right quarter
            startX = x;
            startY = y - markerLength;
            endX = x + markerLength;
            endY = y;
        } else if (quarterX == -1 && quarterY == 1) { // Bottom left quarter
            startX = x - markerLength;
            startY = y;
            endX = x;
            endY = y + markerLength;
        } else { // Bottom right quarter
            startX = x;
            startY = y;
            endX = x + markerLength;
            endY = y + markerLength;
        }

        System.out.println("startX = " + startX);
        System.out.println("startY = " + startY);
        System.out.println("endX = " + endX);
        System.out.println("endY = " + endY);

        return new HourPosition(startX, startY, endX, endY);
    }

    private record HourPosition(int startX, int startY, int endX, int endY) {
    }

    private static boolean isOnClockFace(int x, int y) {
        int distanceSquared = (x - CENTER_X) * (x - CENTER_X) + (y - CENTER_Y) * (y - CENTER_Y);
        return distanceSquared <= CLOCK_RADIUS * CLOCK_RADIUS && distanceSquared >= (CLOCK_RADIUS - HOUR_MARKER_LENGTH) * (CLOCK_RADIUS - HOUR_MARKER_LENGTH);
    }
}