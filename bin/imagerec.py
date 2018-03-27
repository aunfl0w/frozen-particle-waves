#!/usr/bin/python3
# based on example from Greg Chu
# https://github.com/DeepLearningSandbox/DeepLearningSandbox/blob/master/image_recognition/classify.py
#
import sys
import argparse
import numpy as np
from PIL import Image
from io import BytesIO

from keras.preprocessing import image
from keras.applications.resnet50 import ResNet50, preprocess_input, decode_predictions

model = ResNet50(weights='imagenet')
target_size = (224, 224)


def predict(model, img, target_size, top_n=3):
    """Run model prediction on image
    Args:
      model: keras model
      img: PIL format image
      target_size: (w,h) tuple
      top_n: # of predictions to return
    Returns:
      list of predicted labels and their probabilities
    """
    if img.size != target_size:
        img = img.resize(target_size)

    x = image.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    x = preprocess_input(x)
    preds = model.predict(x)
    return decode_predictions(preds, top=top_n)[0]


def print_preds(img, preds):
    """Print predections to stdout
Args:
    image: PIL image
    preds: list of predicted labels and their probabilities
    """
    for pr in preds:
        print('"',pr[1],'","',pr[2],'"',sep='')
        
    



if __name__ == "__main__":
    a = argparse.ArgumentParser()
    a.add_argument("--image", help="path to image")
    args = a.parse_args()

    if args.image is None:
        a.print_help()
        sys.exit(1)

    img = Image.open(args.image)
    preds = predict(model, img, target_size)
    print_preds(img, preds)
