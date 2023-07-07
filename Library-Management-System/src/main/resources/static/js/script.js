const searchInput = document.getElementById('search-input');
const searchButton = document.getElementById('search-button');
const resultsContainer = document.getElementById('results-container');
const prevButton = document.getElementById('prev-button');
const nextButton = document.getElementById('next-button');
const pageInfo = document.getElementById('page-info');
const resultNum = document.getElementById("results-num")
const bookForm = document.getElementById('book-form');
const descriptionInput = document.getElementById('description');
const bookTitleInput = document.getElementById('title');
const bookAuthorInput = document.getElementById('author');
const imageLinkInput = document.getElementById('imageLink');

let currentPage = 0;
let currentQuery = '';

searchButton.addEventListener('click', searchBooks);
prevButton.addEventListener('click', goToPreviousPage);
nextButton.addEventListener('click', goToNextPage);
searchInput.addEventListener('input', handleInput);

function searchBooks() {
	currentQuery = searchInput.value.trim();
	currentPage = 0;
	performSearch(currentQuery, currentPage);
}

function goToPreviousPage() {
	if (currentPage > 0) {
		currentPage--;
		performSearch(currentQuery, currentPage);
	}
}

function goToNextPage() {
	currentPage++;
	performSearch(currentQuery, currentPage);
}

function performSearch(query, page) {
	const startIndex = page * 10;
	const apiUrl = `https://www.googleapis.com/books/v1/volumes?q=${query}&startIndex=${startIndex}&maxResults=10`;
	fetch(apiUrl)
		.then((response) => response.json())
		.then((data) => {
			displayResults(data.items);
			updatePaginationButtons(page, data.totalItems);
			updatePageInfo(page);
		})
		.catch((error) => {
			console.error('Error:', error);
		});
}

function displayResults(items) {
	resultNum.textContent = `Results : ${items.length}`;
	resultsContainer.innerHTML = '';
	if (items && items.length > 0) {
		items.forEach((item) => {
			const { title, authors, description, imageLinks } = item.volumeInfo;

			const card = document.createElement('li');
			card.classList.add('card');

			const thumbnail = document.createElement('img');
			thumbnail.src = getImageUrl(imageLinks, 'thumbnail');
			thumbnail.alt = 'Book Cover';
			card.appendChild(thumbnail);

			const cardContent = document.createElement('div');
			cardContent.classList.add('card-content');

			const bookTitle = document.createElement('h3');
			bookTitle.textContent = title;
			cardContent.appendChild(bookTitle);

			const authorPara = document.createElement('p');
			authorPara.innerHTML = `<strong>Author:</strong> ${authors ? authors.join(', ') : 'N/A'}`;
			cardContent.appendChild(authorPara);

			const descPara = document.createElement('p');
			const limitedDescription = limitDescription(description, 20);
			descPara.textContent = limitedDescription;
			cardContent.appendChild(descPara);

			card.appendChild(cardContent);
			resultsContainer.appendChild(card);
		});
	} else {
		resultsContainer.innerHTML = '<li>No results found.</li>';
	}
}

function getImageUrl(imageLinks, size) {
	if (imageLinks && imageLinks[size]) {
		return imageLinks[size].replace('http://', 'https://');
	}
	return 'no-image.png';
}

function limitDescription(description, limit) {
	if (description && description.length > limit) {
		const words = description.split(' ');
		const limitedWords = words.slice(0, limit);
		const limitedDescription = limitedWords.join(' ') + '...';
		return limitedDescription;
	}
	return description;
}

function updatePaginationButtons(page, totalItems) {
	const maxPages = Math.ceil(totalItems / 10) - 1;
	prevButton.disabled = page <= 0;
	nextButton.disabled = page >= maxPages;
}

function updatePageInfo(page) {
	pageInfo.textContent = `Page :${page + 1}`;
}

function handleInput() {
	const query = searchInput.value.trim();
	if (query.length > 0) {
		getSuggestions(query)
			.then((suggestions) => {
				displaySuggestions(suggestions);
			})
			.catch((error) => {
				console.error('Error:', error);
			});
	} else {
		clearSuggestions();
	}
}

function getSuggestions(query) {
	const apiUrl = `https://www.googleapis.com/books/v1/volumes?q=${query}&maxResults=5`;
	return fetch(apiUrl)
		.then((response) => response.json())
		.then((data) => {
			const suggestions = [];
			if (data.items && data.items.length > 0) {
				data.items.forEach((item) => {
					const { title, authors } = item.volumeInfo;
					const suggestion = `${title} by ${authors ? authors.join(', ') : 'Unknown Author'}`;
					suggestions.push(suggestion);
				});
			}
			return suggestions;
		});
}

function displaySuggestions(suggestions) {
	resultsContainer.innerHTML = '';
	if (suggestions && suggestions.length > 0) {
		suggestions.forEach((suggestion) => {
			const suggestionItem = document.createElement('li');
			suggestionItem.textContent = suggestion;
			suggestionItem.addEventListener('click', () => {
				searchInput.value = suggestion;
				searchBooks();
				clearSuggestions();
			});
			resultsContainer.appendChild(suggestionItem);
		});
	}
}

function clearSuggestions() {
	resultsContainer.innerHTML = '';
}

resultsContainer.addEventListener('click', (event) => {
	const listItem = event.target.closest('li');
	if (listItem) {
		console.log(listItem);
		const title = listItem.querySelector('h3').textContent;
		const author = listItem.querySelector('p:nth-of-type(1)').textContent.replace('Author:', '').trim();
		const description = listItem.querySelector('p:nth-of-type(2)').textContent;
		const imageLink = listItem.querySelector('img').getAttribute('src');

		descriptionInput.value= description;
		bookTitleInput.value= title;
		bookAuthorInput.value= author;
		imageLinkInput.value= imageLink;
		
		bookForm.submit();
	}
});

