package com.devonfw.sample.archunit.general.common;

/**
 * Abstract base class for a {@link AbstractCto CTO} that holds a {@link #getEto() master} {@link AbstractEto ETO}
 * together with other related transfer-objects defined by its concrete sub-class.
 *
 * @param <E> the generic type of the {@link #getEto() master} {@link AbstractEto ETO}.
 */
public abstract class MasterCto<E extends AbstractEto> extends AbstractCto {

  private E eto;

  /**
   * @return the {@link AbstractEto ETO} of the main entity transferred with this {@link MasterCto}.
   */
  public E getEto() {

    return this.eto;
  }

  /**
   * @param master new value of {@link #getEto()}.
   */
  public void setEto(E master) {

    this.eto = master;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    toString(sb);
    return sb.toString();
  }

  /**
   * @param buffer the {@link StringBuilder} where to append the {@link #toString() string representation}.
   */
  protected void toString(StringBuilder buffer) {

    buffer.append(getClass().getSimpleName());
    if (this.eto != null) {
      buffer.append('[');
      buffer.append(this.eto);
      buffer.append(']');
    }
  }

}