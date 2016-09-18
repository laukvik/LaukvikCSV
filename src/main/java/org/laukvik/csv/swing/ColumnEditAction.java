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

import org.laukvik.csv.MetaData;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Morten Laukvik
 */
public class ColumnEditAction extends AbstractAction {

    private MetaData metaData;
    private int columnIndex;
    private Viewer viewer;

    public ColumnEditAction(Viewer viewer, MetaData metaData, int columnIndex) {
        this.metaData = metaData;
        this.columnIndex = columnIndex;
        this.viewer = viewer;
        putValue(AbstractAction.NAME, metaData.getColumn(columnIndex).getName());
//        putValue(AbstractAction.NAME, metaData.getColumn(columnIndex));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        viewer.openColumnEditor(columnIndex);
    }

}
