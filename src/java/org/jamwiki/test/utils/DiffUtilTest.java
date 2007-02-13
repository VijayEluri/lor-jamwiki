/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, version 2.1, dated February 1999.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the latest version of the GNU Lesser General
 * Public License as published by the Free Software Foundation;
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program (LICENSE.txt); if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Based on code generated by Agitar build: Agitator Version 1.0.2.000071 (Build date: Jan 12, 2007) [1.0.2.000071]
 */
package org.jamwiki.test.utils;

import java.util.Vector;
import junit.framework.TestCase;
import org.jamwiki.utils.DiffUtil;

/**
 *
 */
public class DiffUtilTest extends TestCase {

	/**
	 *
	 */
	public void testDiff() throws Throwable {
		Vector result = DiffUtil.diff("", "testDiffUtilOldVersion");
		assertEquals("result.size()", 1, result.size());
	}

	/**
	 *
	 */
	public void testDiff1() throws Throwable {
		Vector result = DiffUtil.diff(null, "testDiffUtilOldVersion");
		assertEquals("result.size()", 1, result.size());
	}

	/**
	 *
	 */
	public void testDiff2() throws Throwable {
		Vector result = DiffUtil.diff("testDiffUtilNewVersion", "testDiffUtil\nOldVersion");
		assertEquals("result.size()", 2, result.size());
	}

	/**
	 *
	 */
	public void testDiff3() throws Throwable {
		Vector result = DiffUtil.diff("testDiffUtilNewVersion", "");
		assertEquals("result.size()", 1, result.size());
	}

	/**
	 *
	 */
	public void testDiff4() throws Throwable {
		Vector result = DiffUtil.diff("testDiffUtil\nNewVersion", null);
		assertEquals("result.size()", 2, result.size());
	}

	/**
	 *
	 */
	public void testDiff5() throws Throwable {
		Vector result = DiffUtil.diff("testDiffUtil\nNewVersion", "testDiffUtilOldVersion");
		assertEquals("result.size()", 2, result.size());
	}

	/**
	 *
	 */
	public void testDiff6() throws Throwable {
		Vector result = DiffUtil.diff("testDiffUtilNewVersion", "testDiffUtilOldVersion");
		assertEquals("result.size()", 1, result.size());
	}

	/**
	 *
	 */
	public void testDiff7() throws Throwable {
		Vector result = DiffUtil.diff("\n", null);
		assertEquals("result.size()", 0, result.size());
	}

	/**
	 *
	 */
	public void testDiff8() throws Throwable {
		Vector result = DiffUtil.diff(null, null);
		assertEquals("result.size()", 0, result.size());
	}

	/**
	 *
	 */
	public void testDiff9() throws Throwable {
		Vector result = DiffUtil.diff("", null);
		assertEquals("result.size()", 0, result.size());
	}

	/**
	 *
	 */
	public void testDiff10() throws Throwable {
		Vector result = DiffUtil.diff(null, "");
		assertEquals("result.size()", 0, result.size());
	}

	/**
	 *
	 */
	public void testDiff11() throws Throwable {
		Vector result = DiffUtil.diff("\n", "");
		assertEquals("result.size()", 0, result.size());
	}

	/**
	 *
	 */
	public void testDiff12() throws Throwable {
		Vector result = DiffUtil.diff("testString", "testString");
		assertEquals("result.size()", 0, result.size());
	}
}
