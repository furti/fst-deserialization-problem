package io.github.furti.fstserializationproblem;

import java.io.Serializable;

public class Pair<Left, Right> implements Serializable
{

    private static final long serialVersionUID = 160174997957476433L;

    public static <Left, Right> Pair<Left, Right> of(Left left, Right right)
    {
        return new Pair<>(left, right);
    }

    private final Left left;
    private final Right right;

    public Pair(Left left, Right right)
    {
        super();
        this.left = left;
        this.right = right;
    }

    public Left getLeft()
    {
        return left;
    }

    public Right getRight()
    {
        return right;
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s)", left, right);
    }

}
