/*
 * %W% %E%
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*****************************************************************************
 * Copyright (c) 2003 Sun Microsystems, Inc.  All Rights Reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistribution of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materails provided with the distribution.
 *
 * Neither the name Sun Microsystems, Inc. or the names of the contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANT OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMEN, ARE HEREBY EXCLUDED.  SUN MICROSYSTEMS, INC. ("SUN") AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS
 * A RESULT OF USING, MODIFYING OR DESTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.  IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES.  HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OUR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed or intended for us in
 * the design, construction, operation or maintenance of any nuclear facility
 *
 *****************************************************************************/
package net.java.games.input;

/**
 * A FIFO queue for input events.
 */
public final class EventQueue {
	private final Event[] queue;

	private int head;
	private int tail;

	/**
	 * This is an internal method and should not be called by applications using the API
	 */
	public EventQueue(int size) {
		queue = new Event[size + 1];
		for (int i = 0; i < queue.length; i++)
			queue[i] = new Event();
	}

	/**
	 * This is an internal method and should not be called by applications using the API
	 */
	synchronized void add(Event event) {
		queue[tail].set(event);
		tail = increase(tail);
	}

	/**
	 * Check if the queue is full
	 * @return true if the queue is full
	 */
	synchronized boolean isFull() {
		return increase(tail) == head;
	}

	/**
	 * This is an internal method and should not be called by applications using the API
	 */
	private int increase(int x) {
		return (x + 1)%queue.length;
	}

	/**
	 * Populates the provided event with the details of the event on the head of the queue.
	 *
	 * @param event The event to populate
	 * @return false if there were no events left on the queue, otherwise true.
	 */
	public synchronized boolean getNextEvent(Event event) {
		if (head == tail)
			return false;
		event.set(queue[head]);
		head = increase(head);
		return true;
	}
}
