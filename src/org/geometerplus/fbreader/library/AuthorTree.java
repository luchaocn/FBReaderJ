/*
 * Copyright (C) 2009-2013 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.fbreader.library;

import java.util.List;

import org.geometerplus.fbreader.book.*;

public class AuthorTree extends LibraryTree {
	public final Author Author;

	AuthorTree(IBookCollection collection, Author author) {
		super(collection);
		Author = author;
	}

	AuthorTree(AuthorListTree parent, Author author, int position) {
		super(parent, position);
		Author = author;
	}

	@Override
	public String getName() {
		return
			Author != Author.NULL ?
				Author.DisplayName :
				Library.resource().getResource("unknownAuthor").getValue();
	}

	@Override
	protected String getStringId() {
		return "@AuthorTree" + getSortKey();
	}

	@Override
	protected String getSortKey() {
		if (Author == null) {
			return null;
		}
		return new StringBuilder()
			.append(" Author:")
			.append(Author.SortKey)
			.append(":")
			.append(Author.DisplayName)
			.toString();
	}

	@Override
	public boolean containsBook(Book book) {
		if (book == null) {
			return false;
		}
		final List<Author> bookAuthors = book.authors();
		return Author.equals(Author.NULL) ? bookAuthors.isEmpty() : bookAuthors.contains(Author);
	}

	@Override
	public Status getOpeningStatus() {
		return Status.ALWAYS_RELOAD_BEFORE_OPENING;
	}

	@Override
	public void waitForOpening() {
		clear();
		for (Book book : Collection.books(Author)) {
			final SeriesInfo seriesInfo = book.getSeriesInfo();
			if (seriesInfo == null) {
				getBookSubTree(book);
			} else {
				getSeriesSubTree(seriesInfo.Title).getBookInSeriesSubTree(book);
			}
		}
	}

	@Override
	public boolean onBookEvent(BookEvent event, Book book) {
		switch (event) {
			default:
			case Added:
				// TODO: implement
				return false;
			case Removed:
				// TODO: implement
				return false;
			case Updated:
				// TODO: implement
				return false;
		}
	}
}
