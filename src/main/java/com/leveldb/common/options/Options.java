package com.leveldb.common.options;

import com.leveldb.common.Cache;
import com.leveldb.common.Comparator;
import com.leveldb.common.Env;
import com.leveldb.common.Logger;

//Options to control the behavior of a database (passed to DB::Open)
public class Options {

	// -------------------
	// Parameters that affect behavior

	// Comparator used to define the order of keys in the table.
	// Default: a comparator that uses lexicographic byte-wise ordering
	//
	// REQUIRES: The client must ensure that the comparator supplied
	// here has the same name and orders keys *exactly* the same as the
	// comparator provided to previous open calls on the same DB.
	public Comparator comparator;

	// If true, the database will be created if it is missing.
	// Default: false
	public boolean create_if_missing;

	// If true, an error is raised if the database already exists.
	// Default: false
	public boolean error_if_exists;

	// If true, the implementation will do aggressive checking of the
	// data it is processing and will stop early if it detects any
	// errors. This may have unforeseen ramifications: for example, a
	// corruption of one DB entry may cause a large number of entries to
	// become unreadable or for the entire DB to become unopenable.
	// Default: false
	public boolean paranoid_checks;

	// Use the specified object to interact with the environment,
	// e.g. to read/write files, schedule background work, etc.
	// Default: Env::Default()
	public Env env;

	// Any internal progress/error information generated by the db will
	// be to written to info_log if it is non-NULL, or to a file stored
	// in the same directory as the DB contents if info_log is NULL.
	// Default: NULL
	public Logger info_log;

	// -------------------
	// Parameters that affect performance

	// Amount of data to build up in memory (backed by an unsorted log
	// on disk) before converting to a sorted on-disk file.
	//
	// Larger values increase performance, especially during bulk loads.
	// Up to two write buffers may be held in memory at the same time,
	// so you may wish to adjust this parameter to control memory usage.
	//
	// Default: 4MB
	public int write_buffer_size;

	// Number of open files that can be used by the DB. You may need to
	// increase this if your database has a large working set (budget
	// one open file per 2MB of working set).
	//
	// Default: 1000
	public int max_open_files;

	// Control over blocks (user data is stored in a set of blocks, and
	// a block is the unit of reading from disk).

	// If non-NULL, use the specified cache for blocks.
	// If NULL, leveldb will automatically create and use an 8MB internal cache.
	// Default: NULL
	public Cache block_cache;

	// Approximate size of user data packed per block. Note that the
	// block size specified here corresponds to uncompressed data. The
	// actual size of the unit read from disk may be smaller if
	// compression is enabled. This parameter can be changed dynamically.
	//
	// Default: 4K
	public int block_size;

	// Number of keys between restart points for delta encoding of keys.
	// This parameter can be changed dynamically. Most clients should
	// leave this parameter alone.
	//
	// Default: 16
	public int block_restart_interval;

	// Compress blocks using the specified compression algorithm. This
	// parameter can be changed dynamically.
	//
	// Default: kSnappyCompression, which gives lightweight but fast
	// compression.
	//
	// Typical speeds of kSnappyCompression on an Intel(R) Core(TM)2 2.4GHz:
	// ~200-500MB/s compression
	// ~400-800MB/s decompression
	// Note that these speeds are significantly faster than most
	// persistent storage speeds, and therefore it is typically never
	// worth switching to kNoCompression. Even if the input data is
	// incompressible, the kSnappyCompression implementation will
	// efficiently detect that and will switch to uncompressed mode.
	public CompressionType compression;

	// Create an Options object with default values for all fields.
	public Options() {
		comparator = (Comparator.BytewiseComparator());
		create_if_missing = false;
		error_if_exists = false;
		paranoid_checks = false;
		env = Env.Default();
		info_log = null;
		write_buffer_size = 4 << 20;
		max_open_files = 1000;
		block_cache = null;
		block_size = 4096;
		block_restart_interval = 16;
		compression = new CompressionType(CompressionType.kNoCompression);// kSnappyCompression);
	}

	// wlu, 2012-7-7, snapshot
	public void Options_(Options other) {
		this.comparator = other.comparator;
		this.create_if_missing = other.create_if_missing;
		this.error_if_exists = other.error_if_exists;
		this.paranoid_checks = other.paranoid_checks;
		this.env = other.env;
		this.info_log = other.info_log;
		this.write_buffer_size = other.write_buffer_size;
		this.max_open_files = other.max_open_files;
		this.block_cache = other.block_cache;
		this.block_size = other.block_size;
		this.block_restart_interval = other.block_restart_interval;
		this.compression = other.compression;
	}

};
