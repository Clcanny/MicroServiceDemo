const express = require('express')
const app = express()
app.get('/health', (req, res) => {  
    res.json({
	status: 'UP'
    })
})
app.get('/number', function(request, response) {
    response.send('{number: 1}')
})
app.listen(8000)
