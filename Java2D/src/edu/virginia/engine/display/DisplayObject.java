package edu.virginia.engine.display;

import edu.virginia.engine.events.Event;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A very basic display object for a java based gaming engine
 *
 * */
public class DisplayObject {

	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

	/* If this display object is visible */
	private boolean visible=true;

	/* describe the x,y position */
	private double xPos, yPos;

	/* describe the gravity */
	private double g = 0.5;

	/* describe the jump acceleration */
	private double jumpAcc = 15;

	/* describe the velocity of Mario */
	private double v;

	/* describe if Object is on ground */
	private boolean onGround;

	/* describe the dimensions */
	private int width, height;



	public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    /* describe the pivot point */
	private double xPivot, yPivot;

	AffineTransform saveTx;

	/* scales the image up or down, 1.0 is the actual size */
	private double scaleX=1;
	private double scaleY=1;

	/* defines the amount (in degrees or radians, your choice) to rotate this object */
	private double rotation=0;

	/* defines how transparent to draw this object. */
	private double alpha=1.0;

	private DisplayObject parent;



	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);

	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);

	}

	public Rectangle getHitBox() {
	    return new Rectangle((int)xPos, (int)yPos, width, height);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public boolean collidesWith(DisplayObject other) {
	    return this.getHitBox().intersects(other.getHitBox());
	}
	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		this.setWidth(displayImage.getWidth());
		this.setHeight(displayImage.getHeight());
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}

	public void setDisplayImage(String imageName){
		setImage(readImage(imageName));
	}

	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<String> pressedKeys) {


        if(pressedKeys.size()>=0) {
            for(String key : pressedKeys) {
                System.out.println(key);

                //jump
                if(key.equals(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
                    if(this.isOnGround()) {
                        this.setV(getV()-this.getJumpAcc());
                        this.setOnGround(false);
                    }
                }

                //move
                if(key.equals(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
                        setxPos(xPos-5);
                }
                if(key.equals(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
                        setxPos(xPos+5);
                }
//                //left rotate
//                if(key.equals(KeyEvent.getKeyText(KeyEvent.VK_A))) {
//                    setRotation(getRotation()+0.1);
//
//                }
//                //right rotate
//                if(key.equals(KeyEvent.getKeyText(KeyEvent.VK_S))) {
//                    setRotation(getRotation()-0.1);
//
//                }
//                //Scale up
//                if(key.equals(KeyEvent.getKeyText(KeyEvent.VK_Q))) {
//                    if(getScaleX()<1.4) {
//                    setScaleX(getScaleX()+0.1);
//                    setScaleY(getScaleY()+0.1);
//                    }
//                }
//                //Scale down
//                if(key.equals(KeyEvent.getKeyText(KeyEvent.VK_W))) {
//                    if(getScaleX()>0.5) {
//                        setScaleX(getScaleX()-0.1);
//                        setScaleY(getScaleY()-0.1);
//                    }
//                }

            }
        }
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {

		if (displayImage != null) {
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;


			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */

			g2d.drawImage(displayImage, 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			g.drawOval(0, 0, 5, 5);


			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);

		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {

	    saveTx = g2d.getTransform();

	    g2d.translate(xPos, yPos);
	    AffineTransform transformer = new AffineTransform();

	    transformer.translate(xPivot, yPivot);
        transformer.rotate(rotation);
        transformer.scale(scaleX, scaleY);
        transformer.translate(-xPivot, -yPivot);

	    g2d.transform(transformer);



	    int type = AlphaComposite.SRC_OVER;
        AlphaComposite composite =
          AlphaComposite.getInstance(type, (float)alpha);
        g2d.setComposite(composite);

	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
	    g2d.translate(-xPos, -yPos);
	    g2d.setTransform(saveTx);

	}


    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public double getScaleX()
    {
        return scaleX;
    }

    public void setScaleX(double scaleX)
    {
        this.scaleX = scaleX;
    }

    public double getRotation()
    {
        return rotation;
    }

    public void setRotation(double rotation)
    {
        this.rotation = rotation;
    }

    public double getScaleY()
    {
        return scaleY;
    }

    public void setScaleY(double scaleY)
    {
        this.scaleY = scaleY;
    }

    public double getAlpha()
    {
        return alpha;
    }

    public void setAlpha(double d)
    {
        this.alpha = d;
    }

    public double getxPos()
    {
        return xPos;
    }

    public void setxPos(double xPos)
    {
        this.xPos = xPos;
    }

    public double getyPos()
    {
        return yPos;
    }

    public void setyPos(double yPos)
    {
        this.yPos = yPos;
    }

    public double getyPivot()
    {
        return yPivot;
    }

    public void setyPivot(double yPivot)
    {
        this.yPivot = yPivot;
    }

    public double getxPivot()
    {
        return xPivot;
    }

    public void setxPivot(double xPivot)
    {
        this.xPivot = xPivot;
    }

    public void setParent(DisplayObject parent)
    {
        this.parent = parent;
    }

    public DisplayObject getParent()
    {
        return this.parent;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public double getG()
    {
        return g;
    }

    public void setG(double g)
    {
        this.g = g;
    }

    public double getJumpAcc()
    {
        return jumpAcc;
    }

    public void setJumpAcc(double jumpAcc)
    {
        this.jumpAcc = jumpAcc;
    }

    public double getV()
    {
        return v;
    }

    public void setV(double v)
    {
        this.v = v;
    }

    public boolean isOnGround()
    {
        return onGround;
    }

    public void setOnGround(boolean bool) {
        this.onGround=bool;
    }

}