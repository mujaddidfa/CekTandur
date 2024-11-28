/* const axios = require('axios');

const analyzePlant = async (imageUrl) => {
    try {
        // Contoh request ke model ML yang dihosting (gunakan URL API Anda sendiri)
        const response = await axios.post('https://your-ml-model-url/predict', {
            imageUrl
        });

        if (response.data.success) {
            return {
                name: response.data.plantName,
                disease: response.data.diseaseName,
                solution: response.data.solution
            };
        } else {
            return { error: 'Plant disease could not be identified' };
        }
    } catch (error) {
        throw new  Error('Error in ML Service: ' + error.message);
    }
};

module.exports = { analyzePlant };
 */