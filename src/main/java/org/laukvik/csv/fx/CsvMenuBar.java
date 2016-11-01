package org.laukvik.csv.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;

import java.io.File;
import java.util.ResourceBundle;

/**
 * JavaFX MenuBar for the CSV application.
 */
public final class CsvMenuBar extends MenuBar {

    /**
     * The Menu for View.
     */
    private final Menu viewMenu;
    /**
     * The Application this MenuBar belongs to.
     */
    private final Main main;
    /**
     * The ResourceBundle.
     */
    private final ResourceBundle bundle;
    /**
     * The Menu for recent files.
     */
    private Menu openRecentMenu;

    /**
     * Creates a new MenuBar for the JavaFX application.
     *
     * @param main the main app
     */
    CsvMenuBar(final Main main) {
        super();
        this.main = main;
        bundle = Builder.getBundle();
        setUseSystemMenuBar(Builder.isMac());
        viewMenu = buildViewMenu(main);
        //
        getMenus().add(buildFileMenu(main));
        getMenus().add(buildEditMenu(main));
        getMenus().add(buildQueryMenu(main));
        getMenus().add(buildInsertMenu(main));
        getMenus().addAll(viewMenu);
        getMenus().add(buildHelpMenu(main));
    }

    /**
     * Builds the file menu.
     *
     * @param main the main instance
     * @return the menu
     */
    private Menu buildFileMenu(final Main main) {
        final Menu fileMenu = new Menu(bundle.getString("file"));
        MenuItem newItem = new MenuItem(bundle.getString("file.new"));
        newItem.setAccelerator(KeyCombination.keyCombination("Meta+n"));
        newItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.newFile();
            }
        });

        MenuItem openItem = new MenuItem(bundle.getString("file.open"));
        openItem.setAccelerator(KeyCombination.keyCombination("Meta+o"));
        openItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleFileOpen();
            }
        });

        // ------ Recent files ------
        openRecentMenu = new Menu(bundle.getString("file.recent"));

        MenuItem saveItem = new MenuItem(bundle.getString("file.save"));
        saveItem.setAccelerator(KeyCombination.keyCombination("Meta+s"));
        MenuItem saveAsItem = new MenuItem(bundle.getString("file.saveas"));
        saveAsItem.setAccelerator(KeyCombination.keyCombination("Meta+s+shift"));
        saveAsItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleSaveAsAction();
            }
        });

        MenuItem importItem = new MenuItem(bundle.getString("file.import"));
        importItem.setAccelerator(KeyCombination.keyCombination("Meta+i"));
        importItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleOpenFileWithOptions();
            }
        });


        final Menu exportMenu = new Menu(bundle.getString("file.export"));

        MenuItem exportJsonItem = new MenuItem(bundle.getString("file.export.json"));
        MenuItem exportXmlItem = new MenuItem(bundle.getString("file.export.xml"));
        MenuItem exportHtmlItem = new MenuItem(bundle.getString("file.export.html"));
        MenuItem resourceBundleItem = new MenuItem(bundle.getString("file.export.resourcebundle"));
        exportJsonItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleExportJsonAction();
            }
        });
        exportXmlItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleExportXmlAction();
            }
        });
        exportHtmlItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleExportHtmlAction();
            }
        });
        resourceBundleItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleOpenResourceBundleAction();
            }
        });

        exportMenu.getItems().addAll(exportJsonItem, exportXmlItem, exportHtmlItem, resourceBundleItem);

        MenuItem printItem = new MenuItem(bundle.getString("file.print"));
        printItem.setAccelerator(KeyCombination.keyCombination("Meta+p"));
        printItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handlePrintAction();
            }
        });
        fileMenu.getItems().addAll(newItem, openItem, openRecentMenu, saveItem, saveAsItem, new SeparatorMenuItem(),
                importItem, exportMenu, new SeparatorMenuItem(), printItem);

        return fileMenu;
    }

    /**
     * Builds the edit menu.
     *
     * @param main the main instance
     * @return the menu
     */
    private Menu buildEditMenu(final Main main) {

        // ----- Edit ------
        final Menu edit = new Menu(bundle.getString("edit"));
        MenuItem cutItem = new MenuItem(bundle.getString("edit.cut"));
        cutItem.setAccelerator(KeyCombination.keyCombination("Meta+x"));
        cutItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleCutAction();
            }
        });

        MenuItem copyItem = new MenuItem(bundle.getString("edit.copy"));
        copyItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleCopyAction();
            }
        });
        copyItem.setAccelerator(KeyCombination.keyCombination("Meta+c"));
        MenuItem pasteItem = new MenuItem(bundle.getString("edit.paste"));
        pasteItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handlePasteAction();
            }
        });
        pasteItem.setAccelerator(KeyCombination.keyCombination("Meta+v"));
        MenuItem deleteItem = new MenuItem(bundle.getString("edit.delete"));
        deleteItem.setAccelerator(KeyCombination.keyCombination("Meta+backspace"));
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleDeleteAction();
            }
        });

        MenuItem moveUpItem = new MenuItem(bundle.getString("edit.moveup"));
        moveUpItem.setAccelerator(KeyCombination.keyCombination("Meta+" + KeyCode.UP));
        moveUpItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleMoveUpAction();
            }
        });
        MenuItem moveDownItem = new MenuItem(bundle.getString("edit.movedown"));
        moveDownItem.setAccelerator(KeyCombination.keyCombination("Meta+" + KeyCode.DOWN));
        moveDownItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleMoveDownAction();
            }
        });
        edit.getItems().addAll(cutItem, copyItem, pasteItem, deleteItem,
                new SeparatorMenuItem(), moveUpItem, moveDownItem);

        return edit;
    }

    /**
     * Builds the query menu.
     *
     * @param main the main instance
     * @return the menu
     */
    private Menu buildQueryMenu(final Main main) {
        // ----- Query ------
        final Menu queryMenu = new Menu(bundle.getString("query"));  // Clear query
        MenuItem newQueryMenuItem = new MenuItem(bundle.getString("query.new"));
        newQueryMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleNewQuery();
            }
        });
        queryMenu.getItems().addAll(newQueryMenuItem);
        return queryMenu;
    }

    /**
     * Builds the insert menu.
     *
     * @param main the main instance
     * @return the menu
     */
    private Menu buildInsertMenu(final Main main) {
        // ----- Insert ------
        final Menu insert = new Menu(bundle.getString("insert"));
        MenuItem newColumnItem = new MenuItem(bundle.getString("insert.column"));
        newColumnItem.setAccelerator(KeyCombination.keyCombination("Meta+i"));
        newColumnItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleNewColumnAction();
            }
        });
        MenuItem newRowItem = new MenuItem(bundle.getString("insert.row"));
        newRowItem.setAccelerator(KeyCombination.keyCombination("Meta+R"));
        newRowItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleNewRowAction();
            }
        });
        MenuItem headersRowItem = new MenuItem(bundle.getString("insert.headers"));
        headersRowItem.setAccelerator(KeyCombination.keyCombination("Meta+H"));
        headersRowItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleNewHeaders();
            }
        });
        insert.getItems().addAll(newColumnItem, newRowItem, headersRowItem);


        return insert;
    }

    /**
     * Builds the view menu.
     *
     * @param main the main instance
     * @return the menu
     */
    private Menu buildViewMenu(final Main main) {
        Menu menu = new Menu(bundle.getString("view"));
        CheckMenuItem viewResultsMenuItem = new CheckMenuItem(bundle.getString("view.results"));
        viewResultsMenuItem.setAccelerator(KeyCombination.keyCombination("Meta+1"));
        viewResultsMenuItem.setSelected(true);
        viewResultsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                setSelectedMode(ViewMode.Results);
                main.handleViewResultsAction();
            }
        });

        CheckMenuItem viewChartMenuItem = new CheckMenuItem(bundle.getString("view.piechart"));
        viewChartMenuItem.setAccelerator(KeyCombination.keyCombination("Meta+2"));
        viewChartMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                setSelectedMode(ViewMode.Chart);
                main.handleViewChartAction();

            }
        });

        // TODO - WRONG NAME
        CheckMenuItem previewChartMenuItem = new CheckMenuItem(bundle.getString("view.preview"));
        previewChartMenuItem.setAccelerator(KeyCombination.keyCombination("Meta+3"));
        previewChartMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                setSelectedMode(ViewMode.Preview);
                main.handleViewPreviewAction();

            }
        });

        CheckMenuItem wikipediaMenuItem = new CheckMenuItem(bundle.getString("view.wikipedia"));
        wikipediaMenuItem.setAccelerator(KeyCombination.keyCombination("Meta+4"));
        wikipediaMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                setSelectedMode(ViewMode.Wikipedia);
                main.handleViewWikipediaAction();

            }
        });

        CheckMenuItem googleMapsMenuItem = new CheckMenuItem(bundle.getString("view.googlemaps"));
        googleMapsMenuItem.setAccelerator(KeyCombination.keyCombination("Meta+5"));
        googleMapsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                setSelectedMode(ViewMode.Maps);
                main.handleViewGoogleMapsAction();

            }
        });

        CheckMenuItem googleSearchMenuItem = new CheckMenuItem(bundle.getString("view.google"));
        googleSearchMenuItem.setAccelerator(KeyCombination.keyCombination("Meta+6"));
        googleSearchMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                setSelectedMode(ViewMode.Search);
                main.handleViewGoogleSearchAction();

            }
        });

        menu.getItems().addAll(viewResultsMenuItem, viewChartMenuItem, previewChartMenuItem,
                wikipediaMenuItem, googleMapsMenuItem, googleSearchMenuItem);

        return menu;
    }

    /**
     * Builds the help menu.
     *
     * @param main the main instance
     * @return the menu
     */
    private Menu buildHelpMenu(final Main main) {
        final Menu help = new Menu(bundle.getString("help"));
        MenuItem aboutMenuItem = new MenuItem(bundle.getString("help.about"));
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                main.handleAboutAction();
            }
        });
        help.getItems().addAll(aboutMenuItem);
        return help;
    }

    /**
     * Builds a menu with all recently loaded files.
     *
     * @param recent the recent object
     */
    public void buildRecentList(final Recent recent) {
        openRecentMenu.getItems().clear();
        for (File file : recent.getList()) {
            MenuItem openRecentItem = new MenuItem(file.getAbsolutePath());
            openRecentItem.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(final ActionEvent t) {
                    main.loadFile(file);
                }
            });
            openRecentMenu.getItems().add(openRecentItem);
        }
        openRecentMenu.getItems().add(new SeparatorMenuItem());
        MenuItem openRecentItem = new MenuItem(bundle.getString("file.recent.clear"));
        openRecentItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent t) {
                recent.clear();
                buildRecentList(recent);
            }
        });
        openRecentMenu.getItems().add(openRecentItem);
    }

    /**
     * Sets the selected view mode.
     *
     * @param viewMode the view mode
     */
    private void setSelectedMode(final ViewMode viewMode) {
        int x = 0;
        for (MenuItem item : viewMenu.getItems()) {
            CheckMenuItem i = (CheckMenuItem) item;
            i.setSelected(x == viewMode.ordinal());
            x++;
        }
    }

}
