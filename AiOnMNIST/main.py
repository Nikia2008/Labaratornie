import tensorflow as tf
import keras
import numpy as np
import matplotlib.pyplot as plt 

from PIL import Image


def create_mnist_model():
    model = keras.Sequential(
        [
            keras.layers.Flatten(input_shape=(28,28)),
            keras.layers.Dense(128, activation="relu"),
            keras.layers.Dropout(0.2),
            keras.layers.Dense(10, activation="softmax")
        ]
    )

    model.compile(
        optimizer="adam",
        loss="sparse_categorical_crossentropy",
        metrics=['accuracy']
    )
    return model


def train_model():

    (x_train, y_train), (x_test, y_test) = keras.datasets.mnist.load_data()

    x_train = x_train / 255.0
    x_test = x_test / 255.0


    model = create_mnist_model()
    model.fit(x_train, y_train, epochs=5, validation_data=(x_test, y_test))

    model.save('digit_reco_model.h5')
    return model



def predict_digit(image_path, model):
    img = Image.open(image_path).convert('L')

    img = img.resize((28, 28))

    img_array = 255 - np.array(img)

    img_array = img_array / 255.0

    plt.imshow(img_array, cmap='gray')
    plt.title('Обработаное изображение!')
    plt.show()

    prediction = model.predict(img_array.reshape(1, 28, 28))
    digit = np.argmax(prediction)
    confidence = np.max(prediction)
    
    return digit, confidence



model = train_model()
digit, confidence = predict_digit('digit1.jpg', model)
