<%@ page import="java.util.List, projet.Book" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Book File</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f9;
        }
        h1 {
            text-align: center;
        }
        form {
            max-width: 500px;
            margin: auto;
            padding: 20px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        label, select, input, button {
            display: block;
            width: 100%;
            margin-bottom: 15px;
        }
        input, select, button {
            padding: 10px;
            font-size: 1rem;
        }
        button {
            background-color: #007BFF;
            color: #fff;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Upload Book File</h1>
    <form method="post" action="uploadBookFile" enctype="multipart/form-data">
        <label for="livreId">Select Book:</label>
        <select name="livreId" required>
    	<% 
        	List<projet.Book> books = (List<projet.Book>) request.getAttribute("books");
        	if (books != null && !books.isEmpty()) {
            	for (projet.Book book : books) {
    	%>
                <option value="<%= book.getId() %>"><%= book.getTitle() %></option>
    	<%
            }
        	} else {
    	%>
        		<option disabled>No books available</option>
    	<%
        	}
    	%>
		</select>
        

        <label for="file">Upload File:</label>
        <input type="file" name="file" accept=".pdf,.epub" required />

        <button type="submit">Upload File</button>
    </form>
</body>
</html>
