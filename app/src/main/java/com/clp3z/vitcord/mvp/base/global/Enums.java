package com.clp3z.vitcord.mvp.base.global;

/**
 * Created by Clelia López on 9/21/16
 */
public class Enums {

    public enum DialogType { ALERT, VIEW, CUSTOM }

    public enum ScreenUnit { PX, DP }

    public enum CustomTypeface {
        ROBOTO_REGULAR(0),
        ROBOTO_MEDIUM(1),
        ROBOTO_BOlD(2),
        ROBOTO_BLACK(3),
        PROXIMA_NOVA_REGULAR(4),
        PROXIMA_NOVA_BOLD(5),
        PROXIMA_NOVA_EXTRA_BOLD(6),
        PROXIMA_NOVA_BLACK(6);

        /**
         * Attributes
         */
        int value;

        CustomTypeface (int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static CustomTypeface getEnum(int value) {
            CustomTypeface result = null;
            for (CustomTypeface typeface: CustomTypeface.values())
                if (typeface.getValue() == value) {
                    result = typeface;
                    break;
                }
            return result;
        }
    }
}
