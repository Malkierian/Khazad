#ifndef TYPES_H_INCLUDED
#define TYPES_H_INCLUDED

struct t_matgloss
{
       string id;
       uint8_t fore; // Annoyingly the offset for this differs between types
       uint8_t back;
       uint8_t bright;
};
struct t_vein
{
    uint32_t vtable;
    int16_t type;
    int16_t assignment[16];
    int16_t unknown;
    uint32_t flags;
};

struct t_matglossPair
{
    int16_t type;
    int16_t index;
};

// raw
struct t_construction_df40d
{
    int16_t x;
    int16_t y;
    int16_t z;
    int16_t unk1;
    int16_t unk2;
    t_matglossPair material; // 4B
//    int16_t mat_type;
//    int16_t mat_idx;
};

// cooked
struct t_construction
{
    uint16_t x;
    uint16_t y;
    uint16_t z;
    t_matglossPair material;
//    int16_t mat_type;
//    int16_t mat_idx;
};

/*
		dword vtable;
		int minx;
		int miny;
		int centerx;
		int maxx;
		int maxy;
		int centery;
		int z;
		dword height_not_used;
		word  mattype;
		word  matgloss;
		word  type; // NOTE: the actual field is in a different place
*/

//raw
struct t_building_df40d
{
    uint32_t vtable;
    uint32_t x1;
    uint32_t y1;
    uint32_t centerx;
    uint32_t x2;
    uint32_t y2;
    uint32_t centery;
    uint32_t z;
    uint32_t height;
    t_matglossPair material;
    // not complete
};

//cooked
struct t_building
{
    uint32_t vtable;

    uint32_t x1;
    uint32_t y1;

    uint32_t x2;
    uint32_t y2;

    uint32_t z;

    t_matglossPair material;

    uint32_t type;
    // FIXME: not complete, we need building presence bitmaps for stuff like farm plots and stockpiles, orientation (N,E,S,W) and state (open/closed)
};

struct t_tree_desc
{
    t_matglossPair material;
    uint16_t x;
    uint16_t y;
    uint16_t z;
};

// FIXME: in order in which the raw vectors appear in df memory, move to XML
enum RawType
{
    Mat_Wood,
    Mat_Stone,
    Mat_Plant,
    Mat_Metal,
    NUM_MATGLOSS_TYPES
};

enum BiomeOffset
{
    eNorthWest,
    eNorth,
    eNorthEast,
    eWest,
    eHere,
    eEast,
    eSouthWest,
    eSouth,
    eSouthEast,
    eBiomeCount
};

// TODO: research this further? consult DF hacker wizards?
union t_designation
{
    uint32_t whole;
    struct {
    unsigned int flow_size : 3; // how much liquid is here?
    unsigned int pile : 1; // stockpile?
/*
 * All the different dig designations... needs more info, probably an enum
 */
    unsigned int dig : 3;
    unsigned int detail : 1;///<- wtf
    unsigned int detail_event : 1;///<- more wtf
    unsigned int hidden :1;

/*
 * This one is rather involved, but necessary to retrieve the base layer matgloss index
 * see http://www.bay12games.com/forum/index.php?topic=608.msg253284#msg253284 for details
 */
    unsigned int geolayer_index :4;
    unsigned int light : 1;
    unsigned int subterranean : 1; // never seen the light of day?
    unsigned int skyview : 1; // sky is visible now, it rains in here when it rains

/*
 * Probably similar to the geolayer_index. Only with a different set of offsets and different data.
 * we don't use this yet
 */
    unsigned int biome : 4;
/*
0 = water
1 = magma
*/
    unsigned int liquid_type : 1;
    unsigned int water_table : 1; // srsly. wtf?
    unsigned int rained : 1; // does this mean actual rain (as in the blue blocks) or a wet tile?
    unsigned int traffic : 2; // needs enum
    unsigned int flow_forbid : 1; // idk wtf bbq
    unsigned int liquid_static : 1;
    unsigned int moss : 1;// I LOVE MOSS
    unsigned int feature_present : 1; // another wtf... is this required for magma pipes to work?
    unsigned int liquid_character : 2; // those ripples on streams?
    } bits;
};

// occupancy flags (rat,dwarf,horse,built wall,not build wall,etc)
union t_occupancy
{
    uint32_t whole;
    struct {
    unsigned int building : 3;// building type... should be an enum?
    // 7 = door
    unsigned int unit : 1;
    unsigned int unit_grounded : 1;
    unsigned int item : 1;
    // splatter. everyone loves splatter.
    unsigned int mud : 1;
    unsigned int vomit :1;
    unsigned int debris1 :1;
    unsigned int debris2 :1;
    unsigned int debris3 :1;
    unsigned int debris4 :1;
    unsigned int blood_g : 1;
    unsigned int blood_g2 : 1;
    unsigned int blood_b : 1;
    unsigned int blood_b2 : 1;
    unsigned int blood_y : 1;
    unsigned int blood_y2 : 1;
    unsigned int blood_m : 1;
    unsigned int blood_m2 : 1;
    unsigned int blood_c : 1;
    unsigned int blood_c2 : 1;
    unsigned int blood_w : 1;
    unsigned int blood_w2 : 1;
    unsigned int blood_o : 1;
    unsigned int blood_o2 : 1;
    unsigned int slime : 1;
    unsigned int slime2 : 1;
    unsigned int blood : 1;
    unsigned int blood2 : 1;
    unsigned int debris5 : 1;
    unsigned int snow : 1;
    } bits;
    struct {
        unsigned int building : 3;// building type... should be an enum?
        // 7 = door
        unsigned int unit : 1;
        unsigned int unit_grounded : 1;
        unsigned int item : 1;
        // splatter. everyone loves splatter.
        unsigned int splatter : 26;
    } unibits;
};

#endif // TYPES_H_INCLUDED