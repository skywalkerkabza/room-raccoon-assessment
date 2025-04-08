const axios = require('axios');

describe('API Testing', () => {
  test('GET /health should return status 200', async () => {
    // Replace with your actual API endpoint
    const response = await axios.get('http://localhost:8080/health');
    expect(response.status).toBe(200);
  });

  // You can add more CRUD tests here as required.
});
