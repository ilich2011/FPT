package org.example.custom;

public class BuildingTypeEnum {
    public enum BuildingType {
        PANEL("Панельный"),
        BRICK("Кирпичный");

        private String title;

        BuildingType(String title) {
            this.title = title;
        }

        public static BuildingType fromString(String title) {
            switch (title) {
                case "Панельный":
                    return PANEL;
                default:
                    return BRICK;
            }
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return getTitle();
        }
    }
}
