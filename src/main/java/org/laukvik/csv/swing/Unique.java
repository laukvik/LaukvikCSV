/*
 * Copyright 2015 Laukviks Bedrifter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.laukvik.csv.swing;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 *
 */
public class Unique<T> {

    private T value;
    private int count;
    private boolean selected;

    public Unique(T value, int count) {
        this.value = value;
        this.count = count;
        this.selected = false;
    }

    public int getCount() {
        return count;
    }

    public T getValue() {
        return value;
    }

    public T getPresentationValue() {
        return value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}