package models;

import textures.ModelTexture;

public class TextureModel {

    private RawModel rawModel;
    private ModelTexture modelTexture;

    public TextureModel(RawModel rawModel, ModelTexture modelTexture) {
        this.rawModel = rawModel;
        this.modelTexture = modelTexture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getModelTexture() {
        return modelTexture;
    }
}
