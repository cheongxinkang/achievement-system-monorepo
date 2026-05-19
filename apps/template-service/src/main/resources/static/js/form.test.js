const { initForm, reindexFields } = require('./form.js');

describe('Dynamic Form Tests', () => {
  beforeEach(() => {
    // Setup a clean DOM before each test
    document.body.innerHTML = `
      <div class="fields-container">
        <!-- Initially empty -->
      </div>
      
      <button name="addTextField">Add Field</button>
      
      <template id="field-template-TEXT">
        <div class="field-item">
            <div class="field-item-header">
                Text Field <span class="field-index">_INDEX_ADD_1</span>
                <button type="button" class="btn-delete">Delete</button>
            </div>
            <div class="field-inputs">
                <label for="fields_INDEX_.prompt">Prompt</label>
                <input type="text" id="fields_INDEX_.prompt" name="fields[_INDEX_].prompt" value="" />
            </div>
        </div>
      </template>
    `;

    // Mock setTimeout so we don't have to wait 300ms for animations
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.clearAllTimers();
    jest.useRealTimers();
  });

  test('should add a new field when Add button is clicked', () => {
    initForm(document);
    
    const addBtn = document.querySelector('button[name="addTextField"]');
    const container = document.querySelector('.fields-container');
    
    // Click to add
    addBtn.click();
    
    const items = container.querySelectorAll('.field-item');
    expect(items.length).toBe(1);
    
    // Verify replacements
    const indexSpan = items[0].querySelector('.field-index');
    expect(indexSpan.textContent).toBe('1');
    
    const input = items[0].querySelector('input');
    expect(input.id).toBe('fields0.prompt');
    expect(input.name).toBe('fields[0].prompt');
  });

  test('should delete a field when delete button is clicked', () => {
    initForm(document);
    const addBtn = document.querySelector('button[name="addTextField"]');
    const container = document.querySelector('.fields-container');
    
    // Add two fields
    addBtn.click();
    addBtn.click();
    
    let items = container.querySelectorAll('.field-item');
    expect(items.length).toBe(2);
    
    // Delete the first field
    const deleteBtn = items[0].querySelector('.btn-delete');
    deleteBtn.click();
    
    // Fast-forward animation timeout
    jest.runAllTimers();
    
    items = container.querySelectorAll('.field-item');
    expect(items.length).toBe(1);
    
    // The remaining field should now be index 0
    const input = items[0].querySelector('input');
    expect(input.id).toBe('fields0.prompt');
    expect(input.name).toBe('fields[0].prompt');
  });

  test('should reindex fields correctly', () => {
    // Manually setup 3 fields out of order
    const container = document.querySelector('.fields-container');
    container.innerHTML = `
      <div class="field-item">
        <span class="field-index">2</span>
        <label for="fields1.prompt"></label>
        <input id="fields1.prompt" name="fields[1].prompt" />
      </div>
      <div class="field-item">
        <span class="field-index">3</span>
        <label for="fields2.prompt"></label>
        <input id="fields2.prompt" name="fields[2].prompt" />
      </div>
    `;

    reindexFields(container);

    const items = container.querySelectorAll('.field-item');
    expect(items[0].querySelector('.field-index').textContent).toBe('1');
    expect(items[0].querySelector('input').name).toBe('fields[0].prompt');
    
    expect(items[1].querySelector('.field-index').textContent).toBe('2');
    expect(items[1].querySelector('input').name).toBe('fields[1].prompt');
  });
});
