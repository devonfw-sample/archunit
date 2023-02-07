package com.devonfw.sample.archunit.general.common;

/**
 * This is the abstract base class for a <em>CTO</em> (composite transfer-object). Such object should contain
 * (aggregate) other transfer-objects or {@link java.util.Collection}s of those. However, a CTO shall never have
 * properties for atomic data (datatypes).<br>
 * Classes extending this class should carry the suffix <code>Cto</code>.
 */
public class AbstractCto {

}
