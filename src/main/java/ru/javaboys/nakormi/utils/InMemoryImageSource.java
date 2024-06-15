package ru.javaboys.nakormi.utils;

import com.vaadin.flow.shared.Registration;
import io.jmix.flowui.data.BindingState;
import io.jmix.flowui.data.ValueSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

public class InMemoryImageSource implements ValueSource<byte[]> {
    private final byte[] bytes;

    public InMemoryImageSource(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        ImageIO.write(image, "png", baos);
        this.bytes = baos.toByteArray();
    }

    @Override
    public byte[] getValue() {
        return bytes;
    }

    @Override
    public void setValue(byte[] value) {
        /* NOP */
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public Registration addValueChangeListener(Consumer<ValueChangeEvent<byte[]>> listener) {
        return null;
    }

    @Override
    public BindingState getState() {
        return BindingState.INACTIVE;
    }

    @Override
    public Registration addStateChangeListener(Consumer<StateChangeEvent> listener) {
        return null;
    }

    @Override
    public Class<byte[]> getType() {
        return byte[].class;
    }
}
