const { db } = require('../config/db');

// Mendapatkan informasi user
exports.getUser = async (req, res) => {
  const { id } = req.params; // ID yang akan dicari
  try {
    // Query untuk mencocokkan field `id` dengan nilai yang diminta
    const usersRef = db.collection('users');
    const querySnapshot = await usersRef.where('id', '==', id).get();

    // Cek apakah dokumen ditemukan
    if (querySnapshot.empty) {
      return res.status(404).json({
        status: 404,
        message: "User not found",
        error: {
          details: "The user not found in database. Try creating an account.",
        },
      });
    }

    const userDoc = querySnapshot.docs[0];
    const userData = userDoc.data();

    return res.status(200).json({
      status: 200,
      message: "Receive data successfully",
      data: userData,
    });
  } catch (error) {
    return res.status(500).json({
      status: 500,
      message: "Failed to fetch user",
      error: {
        details: error.message,
      },
    });
  }
};

//update user
exports.updateUser = async (req, res) => {
  const { id } = req.params; 
  const updates = req.body;

  try {
    // Query untuk menemukan dokumen berdasarkan `id`
    const usersRef = db.collection('users');
    const querySnapshot = await usersRef.where('id', '==', id).get();

    if (querySnapshot.empty) {
      return res.status(404).json({
        status: 404,
        message: "User not found",
        error: {
          details: "The user not found in database. Try creating an account.",
        },
      });
    }

    // Ambil dokumen pertama (karena hanya satu dokumen yang diharapkan)
    const userDoc = querySnapshot.docs[0];
    const userRef = userDoc.ref;

    // Perbarui dokumen
    const updatedAt = new Date().toISOString();
    await userRef.update({ ...updates, updatedAt });

    const updatedDoc = await userRef.get();

    return res.status(200).json({
      status: 200,
      message: "User updated successfully",
      data: updatedDoc.data(),
    });
  } catch (error) {
    return res.status(500).json({
      status: 500,
      message: "Failed to update user",
      error: {
        details: error.message,
      },
    });
  }
};


// Menghapus user
exports.deleteUser = async (req, res) => {
  const { id } = req.params; 

  try {
    // Query untuk menemukan dokumen berdasarkan `id`
    const usersRef = db.collection('users');
    const querySnapshot = await usersRef.where('id', '==', id).get();

    // Jika dokumen tidak ditemukan
    if (querySnapshot.empty) {
      return res.status(404).json({
        status: 404,
        message: "User not found",
        error: {
          details: "The user not found in database. Try creating an account.",
        },
      });
    }

    // Ambil dokumen pertama (karena hanya satu dokumen yang diharapkan)
    const userDoc = querySnapshot.docs[0];
    const userRef = userDoc.ref;

    // Hapus dokumen
    await userRef.delete();

    return res.status(200).json({
      status: 200,
      message: "User deleted successfully",
    });
  } catch (error) {
    return res.status(500).json({
      status: 500,
      message: "Failed to delete user",
      error: {
        details: error.message,
      },
    });
  }
};