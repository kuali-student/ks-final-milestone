package org.kuali.student.common.ui.client.widgets.table.scroll;

/**
 * @author Igor
 */
class TablePredicateFactory {

    static interface Predicate {
        boolean indexCondition(int index, int count);

        int nextIndex(int index);

        MoveType moveType();
    }

    static enum MoveType {
        UP_LEFT,
        DOWN_RIGHT,
    }

    static Predicate UP_LEFT_PREDICATE = new Predicate() {
        @Override
        public boolean indexCondition(int row, int rowCount) {
            return row > 0;
        }

        @Override
        public int nextIndex(int row) {
            return row - 1;
        }

        @Override
        public MoveType moveType() {
            return MoveType.UP_LEFT;
        }
    };

    static Predicate DOWN_RIGHT_PREDICATE = new Predicate() {
        @Override
        public boolean indexCondition(int row, int rowCount) {
            return row + 1 < rowCount;
        }

        @Override
        public int nextIndex(int row) {
            return row + 1;
        }

        @Override
        public MoveType moveType() {
            return MoveType.DOWN_RIGHT;
        }
    };
}
