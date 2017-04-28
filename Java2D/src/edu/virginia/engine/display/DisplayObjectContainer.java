package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class DisplayObjectContainer extends DisplayObject {

    private List<DisplayObject> children = new ArrayList<DisplayObject>();
    private DisplayObject parent;

    public DisplayObjectContainer(String id)
    {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public DisplayObjectContainer(String id, String fileName) {
        super(id, fileName);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

        Graphics2D g2d = (Graphics2D) g;
        applyTransformations(g2d);

        for(DisplayObject child:this.getChildren()) {
            child.draw(g);
        }

        reverseTransformations(g2d);
    }

    @Override
    protected void update(ArrayList<String> pressedKeys) {
        super.update(pressedKeys);
//        for(DisplayObject child:this.getChildren()) {
//            child.update(pressedKeys);
//        }
    }

    public List<DisplayObject> getChildren()
    {
        return children;
    }

    public DisplayObject getChild(String id) {
        for(DisplayObject child : children) {
            if(child.getId().equals(id)) {
                return child;
            }
        }
        return null;
    }

    public DisplayObject getChildByIndex(int index) {
        return children.get(index);
    }

    public void setChildren(List<DisplayObject> children)
    {
        this.children = children;
    }

    public void addChild(DisplayObject child) {
        child.setParent(this);
        children.add(child);
    }

    public void addChildAtIndex(DisplayObject child, int index) {
        child.setParent(this);
        children.add(index, child);
    }

    public void removeChild(String id) {
        for(DisplayObject child : children) {
            if(child.getId().equals(id)) {
                child.setParent(null);
                children.remove(child);
            }
        }
    }

    public void removeByIndex(int index) {
        children.get(index).setParent(null);
        children.remove(index);
    }

    public void removeAll() {
        for(DisplayObject child : children) {
            child.setParent(null);
        }
        children.clear();
    }

    public DisplayObjectContainer getParent() {
        return null;
    }

    public boolean contains(DisplayObject child) {
        if (!children.contains(child)) {
            return false;
        }
        return true;
    }

}
