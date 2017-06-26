const express = require('express')
const app = express()
app.get('/', function(request, response) {
    response.send('{number: 1}')
})
app.listen(8000)
