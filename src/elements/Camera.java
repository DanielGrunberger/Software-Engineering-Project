package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * The type Camera.
 *
 * @author David Zimberknopf & Daniel Grunberger
 */
public class Camera {
    private Point3D _p0;
    private Vector _vectorTowards;
    private Vector _vectorUp;
    private Vector _vectorRight;

    //******* CONSTRUCTORS *******/

    /**
     * Instantiates a new Camera.
     * The third vector, vectorRight is generated by the cross product
     * of the vectorTowards and vectorUp
     *
     * @param p0            the camera location
     * @param vectorTowards the vector towards the camera
     * @param vectorUp      the vector up the camera
     * @throws IllegalArgumentException if vectors up and towards are not orthogonal
     */
    public Camera(Point3D p0, Vector vectorTowards, Vector vectorUp) {
        if (!isZero(vectorTowards.dotProduct(vectorUp)))
            throw new IllegalArgumentException("Vectors Up and Towards are not orthogonal");

        this._p0 = p0;
        this._vectorTowards = vectorTowards.normalized();
        this._vectorUp = vectorUp.normalized();
        this._vectorRight = this._vectorTowards.crossProduct(this._vectorUp);
    }

    //******* GETTERS  *******/

    /**
     * Gets point 0
     *
     * @return the p 0
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * Gets vector towards.
     *
     * @return the vector towards
     */
    public Vector getVectorTowards() {
        return _vectorTowards;
    }

    /**
     * Gets vector up.
     *
     * @return the vector up
     */
    public Vector getVectorUp() {
        return _vectorUp;
    }


    /**
     * Gets vector right.
     *
     * @return the vector right
     */
    public Vector getVectorRight() {
        return _vectorRight;
    }

    //***** FUNCTIONS *******/

    /**
     * Constructs Ray through a single pixel of the screen
     *
     * @param nX             Number of pixels in X axis
     * @param nY             Number of pixels in Y axis
     * @param j              The current pixel in Y axis
     * @param i              The current pixel in X axis
     * @param screenDistance Distance from camera to screen
     * @param screenWidth    Screen width
     * @param screenHeight   Screen height
     * @return the generated ray
     * @throws IllegalArgumentException if distance to screen is zero
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {

        double ry = screenHeight / nY;
        double rx = screenWidth / nX;

        double yi = ((i - nY / 2d) * ry + ry / 2d);
        double xj = ((j - nX / 2d) * rx + rx / 2d);

        Point3D pij = _p0.add(_vectorTowards.scale(screenDistance));

        if (!isZero(xj))
            pij = pij.add(_vectorRight.scale(xj));

        if (!isZero(yi))
            pij = pij.add(_vectorUp.scale(-yi));

        return new Ray(_p0, pij.subtract(_p0));

    }

    /**
     * Constract ray beam through a pixel
     *
     * @param nX             x resolution
     * @param nY             y resolution
     * @param j              pixel x index
     * @param i              pixel y index
     * @param screenDistance the distance from screen
     * @param screenWidth    the width of the screen
     * @param screenHeight   the height of the screen
     * @param numOfRays      the number of rays
     * @return the list of rays
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i, double screenDistance,
                                               double screenWidth, double screenHeight, int numOfRays) {
        if (numOfRays <= 1)
            return List.of(constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth, screenHeight));
        List<Ray> sample_rays = new LinkedList<>();
        double rY = screenHeight / nY;
        double rX = screenWidth / nX;
        double yi = ((i - nY / 2d) * rY);
        double xj = ((j - nX / 2d) * rX);
        for (int row = 0; row < numOfRays; ++row)
            for (int column = 0; column < numOfRays; ++column)
                sample_rays.add(constructRaysThroughPixel(numOfRays, numOfRays, yi, xj, row, column, screenDistance, rX, rY));
        return sample_rays;
    }

    /**
     * Construct a ray through center of a pixel
     *
     * @param nX             x resolution
     * @param nY             y resolution
     * @param yi             pixel corner y
     * @param xj             pixel corner x
     * @param j              sample grid x index
     * @param i              sample grid y index
     * @param screenDistance the distance from screen
     * @param pixelWidth     the width of the screen
     * @param pixelHeight    the height of the screen
     * @return the ray
     */
    private Ray constructRaysThroughPixel(int nX, int nY, double yi, double xj, int j, int i, double screenDistance,
                                          double pixelWidth, double pixelHeight) {
        Point3D pC = _p0.add(_vectorTowards.scale(screenDistance));
        double rY = pixelHeight / nY;
        double rX = pixelWidth / nX;
        double ySampleI = (i * rY + rY / 2d) + yi;
        double xSampleJ = (j * rX + rX / 2d) + xj;
        Point3D pIJ = pC;
        if (!Util.isZero(xSampleJ))
            pIJ = pIJ.add(_vectorRight.scale(xSampleJ));
        if (!Util.isZero(ySampleI))
            pIJ = pIJ.add(_vectorUp.scale(-ySampleI));
        return new Ray(_p0, pIJ.subtract(_p0));
    }
}
