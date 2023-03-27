package com.devonfw.sample.archunit.violation.common;


import com.devonfw.sample.archunit.general.common.AbstractEto;

/**
 * {@link ViolationItem} implementation as {@link AbstractEto}.
 */
public class WrongInterfaceViolationEto extends AbstractEto implements ViolationItem {
   /*
    * they implement an interface with the same simple-name excluding the Eto suffix
    */
}
